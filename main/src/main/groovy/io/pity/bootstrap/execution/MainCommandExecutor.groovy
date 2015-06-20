package io.pity.bootstrap.execution


import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.pity.api.RootCollectorExecutor
import io.pity.api.preprocess.CommandOptions
import io.pity.bootstrap.preprocess.PreProcessorExecutor
import io.pity.bootstrap.provider.InternalCliArgumentProvider

@Slf4j
class MainCommandExecutor implements RootCollectorExecutor {

    InternalCliArgumentProvider cliArgumentProvider;
    PreProcessorExecutor preProcessorExecutor;
    CommandExecutorRunner commandExecutorRunner;

    @Inject
    MainCommandExecutor(InternalCliArgumentProvider cliArgumentProvider, PreProcessorExecutor preProcessorExecutor,
        CommandExecutorRunner commandExecutorRunner) {
        this.cliArgumentProvider = cliArgumentProvider
        this.preProcessorExecutor = preProcessorExecutor
        this.commandExecutorRunner = commandExecutorRunner
    }

    void execute(){
        if (cliArgumentProvider.isCommandExecution()) {
            def commandOptions = cliArgumentProvider.getExecutionCommandOptions()

            log.info("Executing command: {}", commandOptions)
            collectExecutionData(commandOptions)
        }
    }
    private void collectExecutionData(CommandOptions commandOptions) {
        def processedCommandOptions = preProcessorExecutor.processCommandOptions(commandOptions)
        commandExecutorRunner.execute(processedCommandOptions)
    }

}
