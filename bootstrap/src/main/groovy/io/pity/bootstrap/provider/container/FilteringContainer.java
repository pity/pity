package io.pity.bootstrap.provider.container;

import java.io.IOException;
import java.util.Set;

public interface FilteringContainer<T> {
    Set<T> getAvailable() throws IOException;
    Set<T> getFiltered() throws IOException;
}
