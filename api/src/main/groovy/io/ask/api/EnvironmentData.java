package io.ask.api;

import java.util.Map;

public interface EnvironmentData {
    String getCollectorName();

    Map<String, ?> getEnvironmentResults();
}
