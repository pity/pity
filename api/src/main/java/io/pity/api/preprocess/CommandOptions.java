package io.pity.api.preprocess;

import java.util.List;


/**
 * Basic parameters for a command.
 */
public interface CommandOptions {

    /**
     * @return The command to execute
     */
    String getCommand();

    /**
     * The arguments to use
     * @return
     */
    List<String> getArguments();
}
