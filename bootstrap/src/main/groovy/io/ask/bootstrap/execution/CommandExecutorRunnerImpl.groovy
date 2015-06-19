package io.ask.bootstrap.execution


import com.google.inject.Inject
import io.ask.api.execution.CommandExecutor
import io.ask.api.preprocess.CommandOptions
import io.ask.api.reporting.ResultCollector;

public class CommandExecutorRunnerImpl implements CommandExecutorRunner {

    private final Set<CommandExecutor> commandExecutors
    private final ResultCollector resultCollector

    @Inject
    CommandExecutorRunnerImpl(Set<CommandExecutor> commandExecutors, ResultCollector resultCollector) {
        this.resultCollector = resultCollector
        this.commandExecutors = commandExecutors
    }

    @Override
    void execute(CommandOptions commandOptions) {
        def sortedCommandExecutors = commandExecutors.sort { it.commandPrecedence() }.reverse(true)

        for(CommandExecutor commandExecutor : sortedCommandExecutors) {
            if(commandExecutor.willDoWork(commandOptions)) {
                resultCollector.collect(commandExecutor.execute(commandOptions))
                if (commandExecutor.shouldStopExecutionAfter()) {
                    break;
                }
            }
        }
    }
}
