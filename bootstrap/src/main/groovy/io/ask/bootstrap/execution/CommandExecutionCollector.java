package io.ask.bootstrap.execution;

import io.ask.api.execution.CommandExecutionOptions;

public interface CommandExecutionCollector {

    Object executeCommand(CommandExecutionOptions commandOptions);
}
