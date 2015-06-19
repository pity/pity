package io.ask.bootstrap.execution


import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.ask.api.RootCollectorExecutor
import io.ask.api.preprocess.CommandOptions
import io.ask.bootstrap.preprocess.PreProcessorExecutor
import io.ask.bootstrap.provider.InternalCliArgumentProvider

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
