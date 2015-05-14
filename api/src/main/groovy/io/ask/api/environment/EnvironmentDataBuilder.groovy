package io.ask.api.environment

import io.ask.api.environment.EnvironmentDataImpl

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
