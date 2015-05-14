package io.ask.api.execution.preprocess;

import io.ask.api.execution.CommandExecutionOptions;
import io.ask.api.execution.OrderBasedExecution;

public interface CommandPreProcessor extends OrderBasedExecution {

    boolean shouldProcess(CommandExecutionOptions command);

    CommandExecutionOptions processCommand(CommandExecutionOptions command);
}
