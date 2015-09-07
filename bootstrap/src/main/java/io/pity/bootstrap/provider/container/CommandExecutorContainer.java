package io.pity.bootstrap.provider.container;

import io.pity.api.execution.CommandExecutor;

/**
 * This class allows for filtering of command executor. This allows for users to filter out executors.
 */
public interface CommandExecutorContainer extends FilteringContainer<CommandExecutor> {
}
