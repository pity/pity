package io.pity.bootstrap.provider.cli;

import io.pity.api.cli.CliOptionConfigurer;
import io.pity.api.preprocess.CommandOptions;
import io.pity.api.preprocess.CommandOptionsFactory;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CliArgumentProviderImpl implements InternalCliArgumentProvider {
    public static final Logger logger = LoggerFactory.getLogger(CliArgumentProviderImpl.class);

    private final String[] args;
    private final CommandLine optionAccessor;
    private final Options options;

    public CliArgumentProviderImpl(String[] args, Set<CliOptionConfigurer> configurers) {
        this.args = args;

        CommandLineParser parser = new DefaultParser();
        options = new Options();
        configurers.forEach(cli -> cli.configureCli(options));


        try {
            optionAccessor = parser.parse(options, args, true);
            if(null == optionAccessor) {
                throw new ArgumentParseError();
            }
        } catch (ParseException e) {
            logger.error("Unable to parse arguments", e);
            throw new ArgumentParseError("Unable to parse arguments", e);
        }
    }

    public String usage() {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter sw = new StringWriter();
        formatter.printHelp(new PrintWriter(sw), 120, "pity", null, options, 0, 0, null, true);
        return sw.toString();
    }

    public boolean isHelp() {
        return optionAccessor.hasOption("help");
    }

    public boolean isEnvironmentCollectionEnabled() {
        return !optionAccessor.hasOption("disable-env-collection");
    }

    public File getTargetDirectory() {
        if (optionAccessor.hasOption("from")) {
            File dir = new File(optionAccessor.getOptionValue("from"));
            if (dir.isFile()) {
                logger.warn("From was {} which is a file, using parent directory", dir.getAbsolutePath());
                dir = dir.getParentFile();
            }

            if (dir.isDirectory() && dir.exists()) {
                return dir.getAbsoluteFile();
            } else {
                throw new WorkingDirectoryNotFoundError(dir.getAbsolutePath());
            }
        } else {
            return new File("").getAbsoluteFile();
        }
    }

    @Override
    public boolean isOverridePublisher() {
        return optionAccessor.hasOption("publisher");
    }

    @Override
    public String getOverriddenPublisher() {
        return optionAccessor.getOptionValue("publisher");
    }

    @Override
    public List<String> getExcludedCollectors() {
        if(!optionAccessor.hasOption("exclude")){
            return new ArrayList<>();
        }
        return Arrays.asList(optionAccessor.getOptionValues("exclude"));
    }

    public boolean isCommandExecution() {
        return optionAccessor.hasOption("execute");
    }

    public CommandOptions getExecutionCommandOptions() {
        String[] executionOptions = optionAccessor.getOptionValues("execute");
        logger.debug("execute arguments: {}", executionOptions);
        return CommandOptionsFactory.create(Arrays.asList(executionOptions));
    }

    @Override
    public String[] getRawArgs() {
        return args;
    }

    public List<String> getCommandLineOptions() {
        return optionAccessor.getArgList();
    }

    @Override
    public String getRawOption(String param) {
        return optionAccessor.getOptionValue(param);
    }

    @Override
    public String[] getRawOptionArray(String param) {
        return optionAccessor.getOptionValues(param);
    }

    @Override
    public boolean hasRawOption(String param) {
        return optionAccessor.hasOption(param);
    }

    public static class ArgumentParseError extends Error {

        public ArgumentParseError(String s, ParseException e) {
            super(s, e);
        }

        public ArgumentParseError() {
        }
    }

    public static class WorkingDirectoryNotFoundError extends Error {
        WorkingDirectoryNotFoundError(String path) {
            super("Unable to work from " + path);
        }
    }
}

