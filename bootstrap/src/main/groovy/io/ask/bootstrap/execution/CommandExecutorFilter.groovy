package io.ask.bootstrap.execution

import com.google.inject.Inject
import io.ask.api.execution.execute.CommandExecutor

class CommandExecutorFilter {

    @Inject
    CommandExecutorFilter(Set<CommandExecutor> commandExecutorSet) {

    }

    List<CommandExecutor> filter() {
        return []
    }
}
