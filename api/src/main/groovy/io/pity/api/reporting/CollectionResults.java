package io.pity.api.reporting;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;
import java.util.List;
import java.util.Set;


/**
 * An interface to contain all the collected results.
 */
public interface CollectionResults {

    /**
     * @return Set of the {@link EnvironmentData} that was collected
     */
    Set<EnvironmentData> getEnvironmentData();

    /**
     * @return Set of the {@link CommandExecutionResult}s that were collected.
     */
    Set<CommandExecutionResult> getCommandExecutionResults();
}
