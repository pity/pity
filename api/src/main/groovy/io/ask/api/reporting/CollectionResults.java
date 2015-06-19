package io.ask.api.reporting;

import io.ask.api.environment.EnvironmentData;
import io.ask.api.execution.CommandExecutionResult;
import java.util.List;
import java.util.Set;


public interface CollectionResults {
    Set<EnvironmentData> getEnvironmentData();
    List<CommandExecutionResult> getCommandExecutionResults();
}
