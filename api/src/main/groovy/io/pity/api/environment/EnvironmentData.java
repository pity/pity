package io.pity.api.environment;

import java.util.Map;

public interface EnvironmentData {
    String getCollectorName();

    Map<String, ?> getEnvironmentResults();
}
