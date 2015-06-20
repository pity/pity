package io.pity.bootstrap.preprocess;

import io.pity.api.preprocess.CommandOptions;

public interface PreProcessorExecutor {

    CommandOptions processCommandOptions(CommandOptions commandOptions);
}
