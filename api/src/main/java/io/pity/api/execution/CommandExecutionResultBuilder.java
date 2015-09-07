package io.pity.api.execution;

import io.pity.api.execution.internal.CommandExecutionResultImpl;
import io.pity.api.preprocess.CommandOptions;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builder used to create a {@link CommandExecutionResult}
 */
public class CommandExecutionResultBuilder {

    private final String executor;
    private final File resultDir;
    private CommandOptions commandOptions;
    private String stdError;
    private String stdOut;
    private Exception exception;
    private Map<String, String> otherResults = new LinkedHashMap<String, String>();

    public CommandExecutionResultBuilder(String executor, File resultDir) {
        this.executor = executor;
        this.resultDir = resultDir;
    }

    public CommandExecutionResultBuilder setCommandOptions(CommandOptions commandOptions) {
        this.commandOptions = commandOptions;
        return this;
    }

    public CommandExecutionResultBuilder setOutput(String stdOut, String stdError) {
        this.stdError = stdError;
        this.stdOut = stdOut;
        return this;
    }

    public CommandExecutionResultBuilder setException(Exception e) {
        exception = e;
        return this;
    }

    public CommandExecutionResultBuilder addResult(String key, String result) {
        otherResults.put(key, result);
        return this;
    }

    public CommandExecutionResult build() {
        return new CommandExecutionResultImpl(exception, resultDir, stdOut, stdError, commandOptions, executor, otherResults);
    }
}
