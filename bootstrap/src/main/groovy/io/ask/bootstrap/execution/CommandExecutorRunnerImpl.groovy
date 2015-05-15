package io.ask.bootstrap.execution


import com.google.inject.Inject
import io.ask.api.execution.CommandExecutionResults
import io.ask.api.execution.CommandExecutor
import io.ask.api.preprocess.CommandOptions;

public class CommandExecutorRunnerImpl implements CommandExecutorRunner {

    private final Set<CommandExecutor> commandExecutors

    @Inject
    CommandExecutorRunnerImpl(Set<CommandExecutor> commandExecutors) {
        this.commandExecutors = commandExecutors
    }

    @Override
    List<CommandExecutionResults> execute(CommandOptions commandOptions) {
        def commandResults = []

        def sortedCommandExecutors = commandExecutors.sort { it.commandPrecedence() }.reverse(true)

        for(CommandExecutor commandExecutor : sortedCommandExecutors) {
            if(commandExecutor.willDoWork()){
                commandResults << commandExecutor.execute(commandOptions)
                if(commandExecutor.shouldStopExecutionAfter()){
                    break;
                }
            }
        }

        return commandResults;
    }
}
