package io.ask.tasks.execution


import io.ask.api.execution.CommandExecutionResults
import io.ask.api.execution.CommandExecutor
import io.ask.api.preprocess.CommandOptions

class NoopCommandExecutor implements CommandExecutor {
    @Override
    int commandPrecedence() {
        return Integer.MIN_VALUE
    }

    @Override
    boolean shouldStopExecutionAfter() {
        return false
    }

    @Override
    boolean willDoWork() {
        return false
    }

    @Override
    CommandExecutionResults execute(CommandOptions commandOptions) {
        return null
    }
}
