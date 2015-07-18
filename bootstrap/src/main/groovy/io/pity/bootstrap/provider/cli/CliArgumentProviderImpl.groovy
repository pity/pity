package io.pity.bootstrap.provider.cli

import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import io.pity.api.cli.CliOptionConfigurer
import io.pity.api.preprocess.CommandOptions
import io.pity.api.preprocess.CommandOptionsFactory
import org.apache.commons.cli.DefaultParser

@Slf4j
@CompileDynamic
class CliArgumentProviderImpl implements InternalCliArgumentProvider {
    private final String[] args
    private final CliBuilder cliBuilder
    private final OptionAccessor optionAccessor

    CliArgumentProviderImpl(String[] args, Set<CliOptionConfigurer> configurers) {
        this.args = args
        cliBuilder = new CliBuilder(usage: 'ask', parser: new DefaultParser())

        configurers.each { it.configureCli(cliBuilder) }

        optionAccessor = cliBuilder.parse(args)
        if (!optionAccessor) {
            throw new ArgumentParseError()
        }
    }

    public String usage() {
        def oldWriter = cliBuilder.writer;

        StringWriter sw = new StringWriter()
        cliBuilder.writer = new PrintWriter(sw)
        cliBuilder.width = 120
        cliBuilder.usage()

        cliBuilder.writer = oldWriter
        return sw
    }

    public boolean isHelp() {
        return optionAccessor.'help'
    }

    public boolean isEnvironmentCollectionEnabled() {
        return !optionAccessor.'disable-env-collection'
    }

    public File getTargetDirectory() {
        if (optionAccessor.'from') {
            def dir = new File(optionAccessor.'from' as String)
            if (dir.isFile()) {
                log.warn("From was {} which is a file, using parent directory", dir.absolutePath)
                dir = dir.getParentFile()
            }

            if (dir.isDirectory() && dir.exists()) {
                return dir.getAbsoluteFile();
            } else {
                throw new WorkingDirectoryNotFoundError(dir.absolutePath);
            }
        } else {
            return new File('').getAbsoluteFile();
        }
    }

    @Override
    boolean isOverridePublisher() {
        return optionAccessor.'publisher'
    }

    @Override
    String getOverriddenPublisher() {
        return optionAccessor.'publisher'
    }

    public boolean isCommandExecution() {
        return optionAccessor.'execute'
    }

    public CommandOptions getExecutionCommandOptions() {
        def executionOptions = optionAccessor.'execute'
        log.debug("execute arguments: {}", executionOptions)
        if (executionOptions instanceof List) {
            return CommandOptionsFactory.create(executionOptions as List<String>)
        }

        return CommandOptionsFactory.create(executionOptions.toString().split(' ').toList())
    }

    @Override
    Object getRawOption(String option) {
        return optionAccessor.getProperty(option)
    }

    @Override
    String[] getRawArgs() {
        return args;
    }

    List<String> getCommandLineOptions() {
        return optionAccessor.getInner().getOptions().collect { it.getArgName() }
    }

    public static class ArgumentParseError extends Error {

    }

    public static class WorkingDirectoryNotFoundError extends Error {
        WorkingDirectoryNotFoundError(String path) {
            super("Unable to work from " + path)
        }
    }
}

