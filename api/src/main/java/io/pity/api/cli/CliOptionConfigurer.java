package io.pity.api.cli;

import org.apache.commons.cli.Options;

/**
 * This interface allows a plugin to inject new arguments to the commandline parser. When the program is initializing,
 * a simple injector will be created.
 *
 */
public interface CliOptionConfigurer {

    /**
     * Called when the program initializes. Allows a plugin to create new arguments that will be parsed by the program
     * @param options {@link Options} used to parse the options.
     */
    void configureCli(Options options);
}

