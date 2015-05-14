package io.ask.bootstrap.execution
import com.google.inject.Inject
import io.ask.api.execution.CommandExecutionOptions

public class CommandExecutionCollectorImpl implements CommandExecutionCollector {

    private final CommandPreProcessorTransformer bootstrapCommandPreProcessor
    private final CommandExecutorFilter commandExecutorFilter

    @Inject
    CommandExecutionCollectorImpl(CommandPreProcessorTransformer bootstrapCommandPreProcessor,
                                  CommandExecutorFilter commandExecutorFilter) {
        this.commandExecutorFilter = commandExecutorFilter
        this.bootstrapCommandPreProcessor = bootstrapCommandPreProcessor
    }

    @Override
    Object executeCommand(CommandExecutionOptions commandOptions) {
        def processedCommands = bootstrapCommandPreProcessor.preProcessCommands(commandOptions)
        commandExecutorFilter.filter().each {

        }

        return null
    }
}
