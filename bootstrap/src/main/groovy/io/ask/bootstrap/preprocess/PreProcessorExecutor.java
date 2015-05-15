package io.ask.bootstrap.preprocess;

import io.ask.api.preprocess.CommandOptions;

public interface PreProcessorExecutor {

    CommandOptions processCommandOptions(CommandOptions commandOptions);
}
