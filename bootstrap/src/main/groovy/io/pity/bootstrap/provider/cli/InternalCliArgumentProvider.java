package io.pity.bootstrap.provider.cli;

import io.pity.api.cli.CliArgumentProvider;

import java.io.File;


public interface InternalCliArgumentProvider extends CliArgumentProvider {

    boolean isHelp();

    File getTargetDirectory();

    boolean isOverridePublisher();

    String getOverriddenPublisher();

}
