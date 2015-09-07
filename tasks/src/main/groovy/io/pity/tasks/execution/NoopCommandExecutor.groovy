package io.pity.tasks.execution

import com.google.inject.Inject
import io.pity.api.WorkingDirectoryProvider
import io.pity.api.execution.AbstractCommandExecutor
import io.pity.api.execution.CommandExecutionResult
import io.pity.api.execution.CommandExecutor
import io.pity.api.preprocess.CommandOptions

class NoopCommandExecutor extends AbstractCommandExecutor implements CommandExecutor {

    @Inject
    NoopCommandExecutor(WorkingDirectoryProvider workingDirectoryProvider) throws IOException {
        super(workingDirectoryProvider)
    }

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
