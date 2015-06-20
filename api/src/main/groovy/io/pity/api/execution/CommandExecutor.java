package io.pity.api.execution;

import io.pity.api.preprocess.CommandOptions;


public interface CommandExecutor {

    int commandPrecedence();

    boolean shouldStopExecutionAfter();

    boolean willDoWork(CommandOptions commandOptions);

    CommandExecutionResult execute(CommandOptions commandOptions);
}
