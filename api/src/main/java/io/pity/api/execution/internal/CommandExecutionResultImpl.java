package io.pity.api.execution.internal;

import io.pity.api.execution.CommandExecutionResult;
import io.pity.api.preprocess.CommandOptions;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class CommandExecutionResultImpl implements CommandExecutionResult {

    private final String executor;
    private final CommandOptions commandOptions;
    private final String stdError;
    private final String stdOut;
    private final File resultDir;
    private final Exception exception;
    private final Map<String, String> otherResults;

    public CommandExecutionResultImpl(Exception exception, File resultDir, String stdOut, String stdError, CommandOptions commandOptions, String executor, Map<String, String> otherResults) {
        this.exception = exception;
        this.resultDir = resultDir;
        this.stdOut = stdOut;
        this.stdError = stdError;
        this.commandOptions = commandOptions;
        this.executor = executor;
        this.otherResults = Collections.unmodifiableMap(otherResults);
    }

    @Override
    public CommandOptions getCommandExecuted() {
        return commandOptions;
    }

    @Override
    public String getStdError() {
        return stdError;
    }

    @Override
    public String getStdOut() {
        return stdOut;
    }

    @Override
    public File getResultDir() {
        return resultDir;
    }

    @Override
    public Exception getExceptionThrown() {
        return exception;
    }

    @Override
    public String getCommandExecutorClass() {
        return executor;
    }

    @Override
    public Map<String, String> getOtherResults() {
        return otherResults;
    }

    @Override
    public int compareTo(CommandExecutionResult o) {
        return getCommandExecutorClass().compareTo(o.getCommandExecutorClass());
    }
}
