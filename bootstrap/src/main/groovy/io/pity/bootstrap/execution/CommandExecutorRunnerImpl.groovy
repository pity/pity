package io.pity.bootstrap.execution
import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.execution.CommandExecutor
import io.pity.api.preprocess.CommandOptions
import io.pity.api.reporting.ResultCollector
import io.pity.bootstrap.provider.container.CommandExecutorContainer

@CompileStatic
public class CommandExecutorRunnerImpl implements CommandExecutorRunner {

    private final CommandExecutorContainer commandExecutorContainer
    private final ResultCollector resultCollector

    @Inject
    CommandExecutorRunnerImpl(CommandExecutorContainer commandExecutorContainer,
                              ResultCollector resultCollector) {
        this.resultCollector = resultCollector
        this.commandExecutorContainer = commandExecutorContainer
    }

    @Override
    void execute(CommandOptions commandOptions) {
        def sortedCommandExecutors = commandExecutorContainer
            .getAvailable()
            .sort { it.commandPrecedence() }
            .reverse(true)

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
