package io.pity.api.reporting;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;


/**
 * The {@link ResultCollector} is how tasks will provide collected data to the larger system. No not ever create this
 * object yourself, it must be injected into your task.
 */
public interface ResultCollector {
    /**
     * Collecting {@link EnvironmentData} will include it in the results that is published.
     */
    void collect(EnvironmentData environmentData);

    /**
     * Collecting {@link CommandExecutionResult} will include it in the results that is published.
     */
    void collect(CommandExecutionResult commandExecutionResult);
}
