package io.ask.bootstrap.execution;

import io.ask.api.execution.CommandExecutionOptions;

public interface CommandPreProcessorTransformer {

    CommandExecutionOptions preProcessCommands(CommandExecutionOptions commandOptions);
}
