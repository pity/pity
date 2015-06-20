package io.pity.bootstrap.provider

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import io.pity.api.preprocess.CommandOptions
import io.pity.api.preprocess.CommandOptionsFactory
import org.apache.commons.cli.DefaultParser
import org.slf4j.LoggerFactory

@Slf4j
class CliArgumentProviderImpl implements InternalCliArgumentProvider {
    private final String[] args
    private final CliBuilder cliBuilder
    private final OptionAccessor optionAccessor

    CliArgumentProviderImpl(String[] args) {
        this.args = args
        cliBuilder = new CliBuilder(usage: 'ask', parser: new DefaultParser())

        cliBuilder._(longOpt: 'disable-env-collection', 'Disable collection of environmental data')
        cliBuilder._(longOpt: 'execute', args: 1, 'Execute the following command')
        cliBuilder._(longOpt: 'from', args: 1, 'The directory which you want to run against. By default this is the current directory.')
        cliBuilder._(longOpt: 'ticket', args: 1, 'Provides a ticket for this report')
        cliBuilder._(longOpt: 'debug', 'Enable debug logging.')
        cliBuilder._(longOpt: 'silent', 'Disable all logging (except error).')
        cliBuilder.h(longOpt: 'help', 'Show usage information')

        optionAccessor = cliBuilder.parse(args)
        if (!optionAccessor) {
            throw new ArgumentParseError()
        }

        if(optionAccessor.'debug') {
            Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.DEBUG)
        }

        if(optionAccessor.'silent') {
            Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.ERROR)
        }
    }

    public String usage() {
        def oldWriter = cliBuilder.writer;

        StringWriter sw = new StringWriter()
        cliBuilder.writer = new PrintWriter(sw)
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
    String getTicketId() {
        if(optionAccessor.'ticket') {
            return optionAccessor.'ticket'
        } else {
            return null
        }
    }

    @Override
    String[] getRawOption() {
        return args
    }

    public static class ArgumentParseError extends Error {

    }

    public static class WorkingDirectoryNotFoundError extends Error {
        WorkingDirectoryNotFoundError(String path) {
            super("Unable to work from " + path)
        }
    }
}

