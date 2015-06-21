package io.pity.api.execution;

import io.pity.api.preprocess.CommandOptions;


/**
 * All plugins that want to be able to execute a command <strong>must</strong> extend this.
 */
public interface CommandExecutor {

    /**
     * The order that the {@CommandPreProcessor} will be applied.
     *
     * @return an integer, where order of execution is {@link Integer#MAX_VALUE}, {@link Integer#MAX_VALUE} - 1, ...
     */
    int commandPrecedence();

    /**
     * @return If after {@link CommandExecutor#execute(CommandOptions)}, the execution should short circuit
     * the execution return true.
     */
    boolean shouldStopExecutionAfter();

    /**
     *
     * If before {@link CommandExecutor#execute(CommandOptions)}, the execution should skip this {@link CommandExecutor}
     *
     * @param commandOptions Options that will be used to execute the process.
     * @return True is work will be done, if false {@link CommandExecutor#execute(CommandOptions)} will not be called.
     */
    boolean willDoWork(CommandOptions commandOptions);

    /**
     * Execute based on {@link CommandOptions}
     *
     * @param commandOptions Options to be used to execute with
     * @return results of the execution
     */
    CommandExecutionResult execute(CommandOptions commandOptions);
}
