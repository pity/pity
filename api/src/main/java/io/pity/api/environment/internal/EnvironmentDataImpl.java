package io.pity.api.environment.internal;

import groovy.transform.ToString;
import io.pity.api.environment.EnvironmentData;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString
public class EnvironmentDataImpl implements EnvironmentData {

    private final String collectorName;
    private final Map<String, Object> environmentResults = new LinkedHashMap<String, Object>();

    public EnvironmentDataImpl(String collectorName) {
        this.collectorName = collectorName;
    }

    @Override
    public String getCollectorName() {
        return collectorName;
    }

    @Override
    public Map<String, Object> getEnvironmentResults() {
        return environmentResults;
    }

    @Override
    public int compareTo(EnvironmentData o) {
        return collectorName.compareTo(o.getCollectorName());
    }
}
