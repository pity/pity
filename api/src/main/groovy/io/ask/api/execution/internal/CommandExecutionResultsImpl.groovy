package io.ask.api.execution.internal


import io.ask.api.execution.CommandExecutionResults
import io.ask.api.execution.CommandExecutor
import io.ask.api.preprocess.CommandOptions

class CommandExecutionResultsImpl implements CommandExecutionResults {

    private final String executor;
    private final CommandOptions commandOptions
    private final String stdError;
    private final String stdOut;
    private final File resultDir;
    private final Exception exception;
    private final Map<String, String> otherResults;

    CommandExecutionResultsImpl(Exception exception, File resultDir, String stdOut, String stdError,
        CommandOptions commandOptions, String executor, Map<String, String> otherResults) {
        this.exception = exception
        this.resultDir = resultDir
        this.stdOut = stdOut
        this.stdError = stdError
        this.commandOptions = commandOptions
        this.executor = executor
        this.otherResults = Collections.unmodifiableMap(otherResults)
    }

    @Override
    CommandOptions getCommandExecuted() {
        return commandOptions
    }

    @Override
    String getStdError() {
        return stdError
    }

    @Override
    String getStdOut() {
        return stdOut
    }

    @Override
    File getResultDir() {
        return resultDir
    }

    @Override
    Exception getExceptionThrown() {
        return exception
    }

    @Override
    String getCommandExecutorClass() {
        return executor;
    }

    @Override
    Map<String, String> getOtherResults() {
        return otherResults
    }
}
