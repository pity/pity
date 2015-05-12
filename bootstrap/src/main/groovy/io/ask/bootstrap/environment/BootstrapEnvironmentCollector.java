package io.ask.bootstrap.environment;

import io.ask.api.EnvironmentData;

import java.io.File;
import java.util.Set;

public interface BootstrapEnvironmentCollector {

    Set<EnvironmentData> collectEnvironmentData(File workingDir);
}
