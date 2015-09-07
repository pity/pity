package io.pity.bootstrap.execution;

import com.google.inject.Inject;
import io.pity.api.RootCollectorExecutor;
import io.pity.api.preprocess.CommandOptions;
import io.pity.bootstrap.preprocess.PreProcessorExecutor;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCommandExecutor implements RootCollectorExecutor {

    private static final Logger logger = LoggerFactory.getLogger(MainCommandExecutor.class);

    private final InternalCliArgumentProvider cliArgumentProvider;
    private final PreProcessorExecutor preProcessorExecutor;
    private final CommandExecutorRunner commandExecutorRunner;

    @Inject
    public MainCommandExecutor(InternalCliArgumentProvider cliArgumentProvider,
                               PreProcessorExecutor preProcessorExecutor,
                               CommandExecutorRunner commandExecutorRunner) {
        this.cliArgumentProvider = cliArgumentProvider;
        this.preProcessorExecutor = preProcessorExecutor;
        this.commandExecutorRunner = commandExecutorRunner;
    }

    public void execute() {
        if (cliArgumentProvider.isCommandExecution()) {
            CommandOptions commandOptions = cliArgumentProvider.getExecutionCommandOptions();

            logger.info("Executing command: {}", commandOptions);
            collectExecutionData(commandOptions);
        }

    }

    private void collectExecutionData(CommandOptions commandOptions) {
        CommandOptions processedCommandOptions = preProcessorExecutor.processCommandOptions(commandOptions);
        commandExecutorRunner.execute(processedCommandOptions);
    }

    public InternalCliArgumentProvider getCliArgumentProvider() {
        return cliArgumentProvider;
    }

}
