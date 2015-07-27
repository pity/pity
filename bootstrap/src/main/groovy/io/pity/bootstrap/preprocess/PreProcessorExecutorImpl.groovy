package io.pity.bootstrap.preprocess

import com.google.inject.Inject
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j
import io.pity.api.preprocess.CommandOptions
import io.pity.api.preprocess.CommandPreProcessor

@Slf4j
public class PreProcessorExecutorImpl implements PreProcessorExecutor {

    private final Set<CommandPreProcessor> preProcessCommands

    @Inject
    PreProcessorExecutorImpl(Set<CommandPreProcessor> preProcessCommands) {
        this.preProcessCommands = preProcessCommands
    }

    @Override
    CommandOptions processCommandOptions(CommandOptions commandOptions) {
        orderPreProcessors().each { processor -> commandOptions = runPreProcessor(processor, commandOptions) }
        return commandOptions
    }

    /**
     * @return Orders all the {@link CommandPreProcessor} in descending order.
     */
    @CompileDynamic
    Set<CommandPreProcessor> orderPreProcessors() {
        return preProcessCommands.sort { it.commandPrecedence() }.reverse(true)
    }

    CommandOptions runPreProcessor(CommandPreProcessor processor, final CommandOptions commandOptions) {
        def newCommandOptions = processor.processCommand(commandOptions) ?: commandOptions
        if (newCommandOptions != commandOptions) {
            log.info("Command was updated to: {}", commandOptions)
        }
        return newCommandOptions
    }
}
