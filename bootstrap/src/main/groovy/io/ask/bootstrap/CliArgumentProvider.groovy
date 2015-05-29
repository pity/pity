package io.ask.bootstrap

import groovy.util.logging.Slf4j
import io.ask.api.preprocess.CommandOptions
import io.ask.api.preprocess.CommandOptionsFactory
import io.ask.bootstrap.ivy.IvyDependencies
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option

@Slf4j
class CliArgumentProvider {
    private final String[] args
    private final CliBuilder cliBuilder
    private final OptionAccessor optionAccessor

    CliArgumentProvider(String[] args) {
        this.args = args
        cliBuilder = new CliBuilder(usage: 'ask', parser: new DefaultParser())

        cliBuilder._(longOpt: 'disable-env-collection', 'Disable collection of environmental data')
        cliBuilder._(longOpt: 'execute', args: 1, 'Execute the following command')
        cliBuilder._(longOpt: 'from', args: 1, 'The directory which you want to run against. By default this is the current directory.')
        cliBuilder._(longOpt: 'ivy-configuration', args: 1, 'File that contains the external ivy resolver information')
        cliBuilder._(longOpt: 'include', args: Option.UNLIMITED_VALUES, valueSeparator: ',', 'Dependency to be included on the classpath. This should be in form of [group]:[name]:[version]')
        cliBuilder.h(longOpt: 'help', 'Show usage information')

        optionAccessor = cliBuilder.parse(args)
        if(!optionAccessor) {
            throw new ArgumentParseError()
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

    public boolean isHelp(){
        return optionAccessor.'help'
    }

    public boolean isEnvironmentCollectionEnabled(){
        return !optionAccessor.'disable-env-collection'
    }

    public IvyDependencies getIvyConfiguration() {
        def includes = optionAccessor.'includes'
        if(includes){
            return new IvyDependencies(configurationLocation: optionAccessor.'ivy-configuration', dependencies: includes)
        } else {
            return new IvyDependencies()
        }
    }

    public File getTargetDirectory(){
        if(optionAccessor.'from'){
            def dir = new File(optionAccessor.'from' as String)
            if(dir.isFile()) {
                log.warn("From was {} which is a file, using parent directory", dir.absolutePath)
                dir = dir.getParentFile()
            }

            if(dir.isDirectory() && dir.exists()) {
                return dir.getAbsoluteFile();
            } else {
                throw new WorkingDirectoryNotFoundError(dir.absolutePath);
            }
        } else {
            return new File('').getAbsoluteFile();
        }
    }

    public boolean isCommandExecution(){
        return optionAccessor.'execute'
    }

    public CommandOptions getExecutionCommandOptions(){
        def executionOptions = optionAccessor.'execute'
        log.debug("execute arguments: {}", executionOptions)
        if(executionOptions instanceof List) {
            return CommandOptionsFactory.create(executionOptions as List<String>)
        }

        return CommandOptionsFactory.create(executionOptions.toString().split(' ').toList())
    }

    public static class ArgumentParseError extends Error {

    }

    public static class WorkingDirectoryNotFoundError extends Error {
        WorkingDirectoryNotFoundError(String path) {
            super("Unable to work from " + path)
        }
    }
}

