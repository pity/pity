package io.ask.bootstrap.environment;

import io.ask.api.EnvironmentData;

import java.util.Set;

public interface BootstrapEnvironmentCollector {

    Set<EnvironmentData> collectEnvironmentData();
}
