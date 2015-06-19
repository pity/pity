package io.ask.bootstrap.execution;

import io.ask.api.preprocess.CommandOptions;


public interface CommandExecutorRunner {

    void execute(CommandOptions commandOptions);
}
