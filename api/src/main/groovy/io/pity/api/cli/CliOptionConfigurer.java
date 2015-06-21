package io.pity.api.cli;

import groovy.util.CliBuilder;

/**
 * This interface allows a plugin to inject new arguments to the commandline parser. When the program is initializing,
 * a simple injector will be created.
 *
 */
public interface CliOptionConfigurer {

    /**
     * Called when the program initializes. Allows a plugin to create new arguments that will be parsed by the program
     * @param cliBuilder {@link CliBuilder} used to parse the options.
     */
    void configureCli(CliBuilder cliBuilder);
}

