package io.pity.api.execution


import io.pity.api.execution.internal.CommandExecutionResultImpl
import io.pity.api.preprocess.CommandOptions

class CommandExecutionResultBuilder {

    private final String executor;
    private final File resultDir;
    private CommandOptions commandOptions
    private String stdError;
    private String stdOut;
    private Exception exception;
    private Map<String, String> otherResults = [:];

    public CommandExecutionResultBuilder(String executor, File resultDir) {
        this.executor = executor
        this.resultDir = resultDir
    }

    CommandExecutionResultBuilder setCommandOptions(CommandOptions commandOptions) {
        this.commandOptions = commandOptions;
        return this
    }

    CommandExecutionResultBuilder setOutput(String stdOut, stdError){
        this.stdError = stdError
        this.stdOut = stdOut
        return this
    }

    CommandExecutionResultBuilder setException(Exception e) {
        exception = e;
        return this
    }

    CommandExecutionResultBuilder addResult(String key, String result){
        otherResults[key] = result
        return this
    }

    public CommandExecutionResult build() {
        return new CommandExecutionResultImpl(exception, resultDir, stdOut, stdError, commandOptions, executor,
            otherResults)
    }

}
