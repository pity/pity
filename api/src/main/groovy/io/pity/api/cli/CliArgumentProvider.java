package io.pity.api.cli;

import io.pity.api.preprocess.CommandOptions;
import java.io.File;
import java.util.List;


public interface CliArgumentProvider {

    boolean isHelp();

    boolean isEnvironmentCollectionEnabled();

    File getTargetDirectory();

    boolean isCommandExecution();

    CommandOptions getExecutionCommandOptions();

    List<String> getOptions();

    Object getRawOption(String param);

    String[] getRawArgs();
}

