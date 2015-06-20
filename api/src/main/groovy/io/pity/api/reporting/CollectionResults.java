package io.pity.api.reporting;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;
import java.util.List;
import java.util.Set;


public interface CollectionResults {
    Set<EnvironmentData> getEnvironmentData();
    List<CommandExecutionResult> getCommandExecutionResults();
}
