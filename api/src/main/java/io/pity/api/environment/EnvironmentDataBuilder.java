package io.pity.api.environment;

import io.pity.api.environment.internal.EnvironmentDataImpl;

/**
 * Builder to create environment data by {@link EnvironmentCollector}
 */
public class EnvironmentDataBuilder {

    private EnvironmentDataImpl environmentData;

    /**
     * @param collectorName collector name
     */
    private EnvironmentDataBuilder(String collectorName) {
        environmentData = new EnvironmentDataImpl(collectorName);
    }

    /**
     * Builder method to create a {@link EnvironmentDataBuilder}.
     *
     * @param collectorName Name of the collector
     * @return a new {@link EnvironmentDataBuilder}
     */
    public static EnvironmentDataBuilder Builder(String collectorName) {
        return new EnvironmentDataBuilder(collectorName);
    }

    /**
     * Add more data that will be available to the collection mechanism.
     *
     * @param name Name of data collected
     * @param data The result
     * @return this {@link EnvironmentDataBuilder} instance
     */
    public EnvironmentDataBuilder addData(String name, Object data) {
        environmentData.getEnvironmentResults().put(name, data);
        return this;
    }

    /**
     * Get an instance of {@link EnvironmentData}. This may update if {@link EnvironmentDataBuilder#addData(String, Object)}
     * is called after build is called.
     *
     * @return an instance of {@link EnvironmentData}g
     */
    public EnvironmentData build() {
        return environmentData;
    }
}
