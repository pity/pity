package io.pity.api.execution;

import io.pity.api.preprocess.CommandOptions;
import java.io.File;
import java.util.Map;


/**
 * Results from a command execution
 */
public interface CommandExecutionResult extends Comparable<CommandExecutionResult> {

    /**
     * {@link CommandOptions} that was used to execute
     * @return {@link CommandOptions} used.
     */
    CommandOptions getCommandExecuted();

    /**
     * @return Standard Error from the command execution
     */
    String getStdError();

    /**
     * @return Standard Outfrom the command execution
     */
    String getStdOut();

    /**
     * @return Where the output from the command was put
     */
    File getResultDir();

    /**
     * @return Exception that was thrown during the process of running the command
     */
    Exception getExceptionThrown();

    /**
     * @return Name of the {@link AbstractCommandExecutor} used to generate these results
     */
    String getCommandExecutorClass();

    /**
     * @return Get all other results
     */
    Map<String, String> getOtherResults();

}
