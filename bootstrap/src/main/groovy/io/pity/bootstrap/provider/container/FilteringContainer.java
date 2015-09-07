package io.pity.bootstrap.provider.container;

import java.util.Set;

/**
 * Useful for items that need to be filtered out by the users.
 *
 * @param <T> Object that will be returned by the filter.
 */
public interface FilteringContainer<T> {
    Set<T> getAvailable();
    Set<T> getFiltered();
}
