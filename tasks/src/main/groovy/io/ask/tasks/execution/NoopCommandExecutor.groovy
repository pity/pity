package io.ask.tasks.execution


import io.ask.api.execution.CommandExecutionResult
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
    boolean willDoWork(CommandOptions commandOptions) {
        return false
    }

    @Override
    CommandExecutionResult execute(CommandOptions commandOptions) {
        return null
    }
}
