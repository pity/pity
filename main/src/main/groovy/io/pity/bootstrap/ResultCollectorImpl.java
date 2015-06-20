package io.pity.bootstrap;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ResultCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ResultCollectorImpl implements ResultCollector, CollectionResults {

    Set<EnvironmentData> environmentDataSet = Collections.synchronizedSet(new HashSet<EnvironmentData>());
    List<CommandExecutionResult> commandExecutionResultSet = Collections.synchronizedList(new ArrayList<CommandExecutionResult>());

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
    public List<CommandExecutionResult> getCommandExecutionResults() {
        return commandExecutionResultSet;
    }
}
