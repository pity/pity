package io.pity.api;

import io.pity.api.preprocess.CommandOptions;
import java.io.File;


public interface CliArgumentProvider {

    boolean isHelp();

    boolean isEnvironmentCollectionEnabled();

    File getTargetDirectory();

    boolean isCommandExecution();

    CommandOptions getExecutionCommandOptions();

    String getTicketId();

    String[] getRawOption();
}

