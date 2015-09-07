package io.pity.bootstrap.provider.cli;

import io.pity.api.cli.CliArgumentProvider;

import java.io.File;
import java.util.List;


public interface InternalCliArgumentProvider extends CliArgumentProvider {

    boolean isHelp();

    File getTargetDirectory();

    boolean isOverridePublisher();

    String getOverriddenPublisher();

    List<String> getExcludedCollectors();

}
