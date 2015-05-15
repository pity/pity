package io.ask.api.execution;

import io.ask.api.preprocess.CommandOptions;


public interface CommandExecutor {

    int commandPrecedence();

    boolean shouldStopExecutionAfter();

    boolean willDoWork();

    CommandExecutionResults execute(CommandOptions commandOptions);
}
