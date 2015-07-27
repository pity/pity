package io.pity.bootstrap.preprocess;

import io.pity.api.preprocess.CommandOptions;

/**
 * Executes all of the {@link CommandOptions} in descending order.
 */
public interface PreProcessorExecutor {

    CommandOptions processCommandOptions(CommandOptions commandOptions);
}
