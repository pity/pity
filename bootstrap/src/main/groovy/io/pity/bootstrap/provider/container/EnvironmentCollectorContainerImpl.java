package io.pity.bootstrap.provider.container;

import com.google.inject.Inject;
import io.pity.api.environment.EnvironmentCollector;
import io.pity.bootstrap.injection.PropertyFinder;

import java.util.Set;

public class EnvironmentCollectorContainerImpl extends AbstractContainer<EnvironmentCollector>
    implements EnvironmentCollectorContainer {

    public static final String TASK_EXCLUDE_NAME = "pity.task.exclude";

    @Inject
    EnvironmentCollectorContainerImpl(Set<EnvironmentCollector> allEnvironmentCollector, PropertyFinder propertyFinder) {
        super(propertyFinder, TASK_EXCLUDE_NAME, allEnvironmentCollector);
    }
}
