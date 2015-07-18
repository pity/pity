package io.pity.bootstrap.provider.container;

import com.google.inject.Inject;
import io.pity.api.execution.CommandExecutor;
import io.pity.bootstrap.injection.PropertyFinder;

import java.util.Set;

public class CommandExecutorContainerImpl extends AbstractContainer<CommandExecutor> implements CommandExecutorContainer {

    public static final String COMMAND_EXCLUDE_NAME = "pity.command.exclude";

    @Inject
    public CommandExecutorContainerImpl(Set<CommandExecutor> allCommandExecutor, PropertyFinder propertyFinder) {
        super(propertyFinder, COMMAND_EXCLUDE_NAME, allCommandExecutor);
    }
}
