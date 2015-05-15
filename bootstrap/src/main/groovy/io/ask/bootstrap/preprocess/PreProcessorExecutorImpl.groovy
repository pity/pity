package io.ask.bootstrap.preprocess
import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.ask.api.preprocess.CommandOptions
import io.ask.api.preprocess.CommandPreProcessor

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

    Set<CommandPreProcessor> orderPreProcessors() {
        return preProcessCommands.sort { -1 * it.commandPrecedence() }
    }

    CommandOptions runPreProcessor(CommandPreProcessor processor, final CommandOptions commandOptions) {
        def newCommandOptions = processor.processCommand(commandOptions) ?: commandOptions
        if (newCommandOptions != commandOptions) {
            log.info("Command was updated to: {}", commandOptions)
        }
        return newCommandOptions
    }
}
