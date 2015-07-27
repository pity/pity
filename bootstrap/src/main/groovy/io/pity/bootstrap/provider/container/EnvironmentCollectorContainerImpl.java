package io.pity.bootstrap.provider.container;

import com.google.inject.Inject;
import io.pity.api.environment.EnvironmentCollector;
import io.pity.bootstrap.injection.PropertyFinder;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;

import java.util.Set;

public class EnvironmentCollectorContainerImpl extends AbstractContainer<EnvironmentCollector>
    implements EnvironmentCollectorContainer {

    public static final String TASK_EXCLUDE_NAME = "pity.task.exclude";

    @Inject
    EnvironmentCollectorContainerImpl(InternalCliArgumentProvider cliArgumentProvider,
                                      PropertyFinder propertyFinder,
                                      Set<EnvironmentCollector> allEnvironmentCollector) {
        super(cliArgumentProvider, propertyFinder, TASK_EXCLUDE_NAME, allEnvironmentCollector);
    }
}
