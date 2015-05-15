package io.ask.bootstrap.execution;

import io.ask.api.execution.CommandExecutionResults;
import io.ask.api.preprocess.CommandOptions;
import java.util.List;


public interface CommandExecutorRunner {

    List<CommandExecutionResults> execute(CommandOptions commandOptions);
}
