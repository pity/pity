package io.pity.bootstrap.preprocess;

import com.google.inject.Inject;
import io.pity.api.preprocess.CommandOptions;
import io.pity.api.preprocess.CommandPreProcessor;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PreProcessorExecutorImpl implements PreProcessorExecutor {

    private static final Logger logger = LoggerFactory.getLogger(PreProcessorExecutorImpl.class);

    private final Set<CommandPreProcessor> preProcessCommands;

    @Inject
    public PreProcessorExecutorImpl(Set<CommandPreProcessor> preProcessCommands) {
        this.preProcessCommands = preProcessCommands;
    }

    @Override
    public CommandOptions processCommandOptions(final CommandOptions commandOptions) {
        CommandOptions nextCommandOption = commandOptions;
        for (CommandPreProcessor commandPreProcessor : orderPreProcessors()) {
            nextCommandOption = runPreProcessor(commandPreProcessor, commandOptions);
        }
        return nextCommandOption;
    }

    /**
     * @return Orders all the {@link CommandPreProcessor} in descending order.
     */
    public List<CommandPreProcessor> orderPreProcessors() {
        return preProcessCommands.stream()
            .sorted((o1, o2) -> -1 * Integer.compare(o1.commandPrecedence(), o2.commandPrecedence()))
            .collect(Collectors.toList());
    }

    public CommandOptions runPreProcessor(CommandPreProcessor processor, final CommandOptions commandOptions) {
        final CommandOptions command = processor.processCommand(commandOptions);
        CommandOptions newCommandOptions = DefaultGroovyMethods.asBoolean(command) ? command : commandOptions;
        if (!newCommandOptions.equals(commandOptions)) {
            logger.info("Command was updated to: {}", commandOptions);
        }
        return newCommandOptions;
    }
}
