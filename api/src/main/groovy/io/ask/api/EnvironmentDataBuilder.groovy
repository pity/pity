package io.ask.api

import io.ask.api.internal.EnvironmentDataImpl

class EnvironmentDataBuilder {
    EnvironmentDataImpl environmentData
    private EnvironmentDataBuilder(String collectorName) {
        environmentData = new EnvironmentDataImpl(collectorName: collectorName)
    }

    public static EnvironmentDataBuilder Builder(String collectorName) {
        return new EnvironmentDataBuilder(collectorName);
    }

    public EnvironmentDataBuilder addData(String name, Object data) {
        environmentData.environmentResults[name] = data
        return this
    }

    public EnvironmentData build() {
        return environmentData;
    }
}
