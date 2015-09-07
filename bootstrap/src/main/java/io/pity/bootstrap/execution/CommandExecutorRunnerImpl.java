package io.pity.bootstrap.execution;

import com.google.inject.Inject;
import io.pity.api.execution.CommandExecutor;
import io.pity.api.preprocess.CommandOptions;
import io.pity.api.reporting.ResultCollector;
import io.pity.bootstrap.provider.container.CommandExecutorContainer;

import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutorRunnerImpl implements CommandExecutorRunner {

    private final CommandExecutorContainer commandExecutorContainer;
    private final ResultCollector resultCollector;

    @Inject
    public CommandExecutorRunnerImpl(CommandExecutorContainer commandExecutorContainer,
                                     ResultCollector resultCollector) {
        this.resultCollector = resultCollector;
        this.commandExecutorContainer = commandExecutorContainer;
    }

    @Override
    public void execute(CommandOptions commandOptions) {
        List<CommandExecutor> sortedCommandExecutors = commandExecutorContainer.getAvailable()
            .stream()
            .sorted((co1, co2) -> -1 * Integer.compare(co1.commandPrecedence(), co2.commandPrecedence()))
            .collect(Collectors.toList());

        for (CommandExecutor commandExecutor : sortedCommandExecutors) {
            if (commandExecutor.willDoWork(commandOptions)) {
                resultCollector.collect(commandExecutor.execute(commandOptions));
                if (commandExecutor.shouldStopExecutionAfter()) {
                    break;
                }

            }

        }

    }
}
