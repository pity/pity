package io.pity.bootstrap.execution;

import io.pity.api.preprocess.CommandOptions;


public interface CommandExecutorRunner {

    void execute(CommandOptions commandOptions);
}
