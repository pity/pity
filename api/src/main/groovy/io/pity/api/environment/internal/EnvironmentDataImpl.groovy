package io.pity.api.environment.internal
import groovy.transform.ToString
import io.pity.api.environment.EnvironmentData

@ToString
public class EnvironmentDataImpl implements EnvironmentData {

    String collectorName;
    Map<String, ?> environmentResults = [:];

    @Override
    public String getCollectorName() {
        return collectorName;
    }

    @Override
    public Map<String, ?> getEnvironmentResults() {
        return environmentResults;
    }
}
