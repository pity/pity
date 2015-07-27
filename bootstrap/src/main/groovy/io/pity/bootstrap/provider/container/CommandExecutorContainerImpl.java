package io.pity.bootstrap.provider.container;

import com.google.inject.Inject;
import io.pity.api.execution.CommandExecutor;
import io.pity.bootstrap.injection.PropertyFinder;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;

import java.util.Set;

public class CommandExecutorContainerImpl extends AbstractContainer<CommandExecutor> implements CommandExecutorContainer {

    public static final String COMMAND_EXCLUDE_NAME = "pity.command.exclude";

    @Inject
    public CommandExecutorContainerImpl(InternalCliArgumentProvider cliArgumentProvider,
                                        PropertyFinder propertyFinder,
                                        Set<CommandExecutor> allCommandExecutor) {
        super(cliArgumentProvider, propertyFinder, COMMAND_EXCLUDE_NAME, allCommandExecutor);
    }
}
