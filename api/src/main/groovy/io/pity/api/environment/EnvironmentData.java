package io.pity.api.environment;

import java.util.Map;

/**
 * The format that a {@link EnvironmentCollector} will return when {@link EnvironmentCollector#collectEnvironmentData()}
 */
public interface EnvironmentData {

    /**
     * Get the name of the collector that collected the environment data
     * @return Name of the collection
     */
    String getCollectorName();

    /**
     * Get the results of all environmental data collected by a {@link EnvironmentCollector}
     * @return {@link Map} with key being the name of the environment data, and the value with what was returned.
     */
    Map<String, ?> getEnvironmentResults();
}
