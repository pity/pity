package io.ask.api.execution.execute;

import io.ask.api.execution.CommandExecutionOptions;
import io.ask.api.execution.OrderBasedExecution;

public interface CommandExecutor extends OrderBasedExecution {

    /**
     * Will always be called after {@link #execute(CommandExecutionOptions)}
     * @return true if the the rest of the CommandExecutor chain should be evaluated
     */
    boolean shouldStopAfterExecution();

    boolean acceptsCommand(CommandExecutionOptions commandExecutionOptions);

    CommandExecutionResult execute(CommandExecutionOptions commandExecutionOptions);
}
