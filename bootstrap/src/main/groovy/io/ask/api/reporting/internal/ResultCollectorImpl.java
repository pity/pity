package io.ask.api.reporting.internal;

import io.ask.api.environment.EnvironmentData;
import io.ask.api.execution.CommandExecutionResult;
import io.ask.api.reporting.CollectionResults;
import io.ask.api.reporting.ResultCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;


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