package io.pity.bootstrap;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ResultCollector;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Used to contains all of the collectors.
 */
public class ResultCollectorImpl implements ResultCollector, CollectionResults {

    Set<EnvironmentData> environmentDataSet = Collections.synchronizedSet(new TreeSet<EnvironmentData>());
    Set<CommandExecutionResult> commandExecutionResultSet = Collections.synchronizedSet(new TreeSet<CommandExecutionResult>());

    @Override
    public void collect(EnvironmentData environmentData) {
        environmentDataSet.add(environmentData);
    }

    @Override
    public void collect(CommandExecutionResult commandExecutionResult) {
        commandExecutionResultSet.add(commandExecutionResult);
    }

    @Override
    public Set<EnvironmentData> getEnvironmentData() {
        return environmentDataSet;
    }

    @Override
    public Set<CommandExecutionResult> getCommandExecutionResults() {
        return commandExecutionResultSet;
    }
}
