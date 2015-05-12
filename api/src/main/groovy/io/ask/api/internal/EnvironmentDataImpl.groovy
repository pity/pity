package io.ask.api.internal

import groovy.transform.ToString
import io.ask.api.EnvironmentData

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
