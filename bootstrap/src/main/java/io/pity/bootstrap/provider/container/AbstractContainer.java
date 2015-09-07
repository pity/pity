package io.pity.bootstrap.provider.container;

import io.pity.bootstrap.injection.PropertyFinder;
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The abstract class that will provide {@link FilteringContainer<T>}.
 * @param <T>
 */
abstract public class AbstractContainer<T> implements FilteringContainer<T> {

    public static final Logger log = LoggerFactory.getLogger(AbstractContainer.class);

    private final InternalCliArgumentProvider cliArgumentProvider;
    private final PropertyFinder propertyFinder;
    private final String propertyName;
    private final Set<T> originalSet;

    public AbstractContainer(InternalCliArgumentProvider cliArgumentProvider, PropertyFinder propertyFinder,
                             String propertyName, Set<T> originalSet) {
        this.cliArgumentProvider = cliArgumentProvider;
        this.propertyFinder = propertyFinder;
        this.propertyName = propertyName;
        this.originalSet = originalSet;
    }

    private Set<T> filter() throws IOException {
        List<String> filteredClasses = new ArrayList<String>();
        filteredClasses.addAll(propertyFinder.findProperties(propertyName));
        filteredClasses.addAll(cliArgumentProvider.getExcludedCollectors());
        log.debug("Filtered classes: {}", filteredClasses);


        Set<T> filteredCommandExecutors = new HashSet<T>();
        for(T executionClass: originalSet) {
            String fullName = executionClass.getClass().getName();
            if (!filteredClasses.contains(fullName)) {
                filteredCommandExecutors.add(executionClass);
            }
        }
        return filteredCommandExecutors;
    }

    public Set<T> getAvailable() {
        try {
            return filter();
        } catch (IOException e) {
            throw new RuntimeException("Unable to process properties", e);
        }
    }

    public Set<T> getFiltered() {
        return new HashSet<>(CollectionUtils.disjunction(originalSet, getAvailable()));
    }
}
