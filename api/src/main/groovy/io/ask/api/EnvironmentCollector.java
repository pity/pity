package io.ask.api;

import java.io.File;

public interface EnvironmentCollector {
    EnvironmentData collect(File workingDirectory);
}
