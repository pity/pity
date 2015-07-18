package io.pity.bootstrap.provider.container;

import io.pity.bootstrap.injection.PropertyFinder;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class AbstractContainer<T> implements FilteringContainer<T> {

    private final PropertyFinder propertyFinder;
    private final String propertyName;
    private final Set<T> originalSet;

    public AbstractContainer(PropertyFinder propertyFinder, String propertyName, Set<T> originalSet) {
        this.propertyFinder = propertyFinder;
        this.propertyName = propertyName;
        this.originalSet = originalSet;
    }

    private Set<T> filter() throws IOException {
        List<String> properties = propertyFinder.findProperties(propertyName);
        Set<T> filteredCommandExecutors = new HashSet<T>();
        for(T executionClass: originalSet) {
            String fullName = executionClass.getClass().getName();
            if (!properties.contains(fullName)) {
                filteredCommandExecutors.add(executionClass);
            }
        }
        return filteredCommandExecutors;
    }

    public Set<T> getAvailable() throws IOException {
        return filter();
    }

    public Set<T> getFiltered() throws IOException {
        return new HashSet<T>(CollectionUtils.disjunction(originalSet, getAvailable()));
    }
}
