package io.pity.api.cli;

import groovy.util.CliBuilder;

public interface CliOptionConfigurer {

    void configureCli(CliBuilder cliBuilder);
}

