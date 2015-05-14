package io.ask.api.environment;

import java.util.Map;

public interface EnvironmentData {
    String getCollectorName();

    Map<String, ?> getEnvironmentResults();
}
