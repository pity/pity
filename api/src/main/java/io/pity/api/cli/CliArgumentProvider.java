package io.pity.api.cli;

import io.pity.api.preprocess.CommandOptions;

import java.util.List;


/**
 * An interface to allow plugins to inject the cli options.
 */
public interface CliArgumentProvider {

    /**
     * Is environment collection enabled?
     * @return true if environment data should be collected
     */
    boolean isEnvironmentCollectionEnabled();

    /**
     * Should the command execution run?
     * @return true if command will be executed.
     */
    boolean isCommandExecution();

    /**
     * The options to execute commands with
     * @return A CommandOptions object used to execute params
     */
    CommandOptions getExecutionCommandOptions();

    /**
     * The command line options
     * @return List of command line options
     */
    List<String> getCommandLineOptions();

    /**
     * Method to get option from the CLI parser. If a plugin needs to provide a new option see {@link CliArgumentProvider}
     * @param param name of the option
     * @return raw option, you must know the type
     */
    Object getRawOption(String param);

    /**
     * The raw arguments provided to the program.
     * @return
     */
    String[] getRawArgs();
}

