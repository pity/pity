package io.ask.api


import io.ask.api.preprocess.CommandOptions

public interface CliArgumentProvider {

    public boolean isHelp();

    public boolean isEnvironmentCollectionEnabled();

    public File getTargetDirectory();

    public boolean isCommandExecution();

    public CommandOptions getExecutionCommandOptions();
}

