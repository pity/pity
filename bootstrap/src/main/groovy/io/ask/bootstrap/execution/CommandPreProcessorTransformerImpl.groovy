package io.ask.bootstrap.execution

import com.google.inject.Inject
import io.ask.api.execution.CommandExecutionOptions
import io.ask.api.execution.preprocess.CommandPreProcessor;

public class CommandPreProcessorTransformerImpl implements CommandPreProcessorTransformer {

    private final Set<CommandPreProcessor> preProcessCommands

    @Inject
    CommandPreProcessorTransformerImpl(Set<CommandPreProcessor> preProcessCommands) {
        this.preProcessCommands = preProcessCommands
    }

    @Override
    CommandExecutionOptions preProcessCommands(CommandExecutionOptions commandOptions) {

        CommandExecutionOptions processedCommand = commandOptions

        preProcessCommands
            .sort { -1 * it.commandPresence() }
            .each { CommandPreProcessor processor -> processedCommand = runPreProcessor(processor, processedCommand) }

        return processedCommand
    }

    private CommandExecutionOptions runPreProcessor(CommandPreProcessor processor, CommandExecutionOptions commandString) {
        if (processor.shouldProcess(commandString)) {
            commandString = processor.processCommand(commandString)
        }
        return commandString
    }
}
