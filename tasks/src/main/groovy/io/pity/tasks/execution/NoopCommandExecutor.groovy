package io.pity.tasks.execution


import io.pity.api.execution.CommandExecutionResult
import io.pity.api.execution.CommandExecutor
import io.pity.api.preprocess.CommandOptions

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
