package io.pity.bootstrap.provider.cli;

import io.pity.api.cli.CliOptionConfigurer;
import io.pity.bootstrap.injection.injectors.TaskInjector;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DefaultCliOptionConfigurer implements CliOptionConfigurer {
    @Override
    public void configureCli(Options options) {
        options.addOption(
            Option.builder()
                .longOpt("disable-env-collection")
                .desc("Disable collection of environmental data")
                .build());

        options.addOption(Option.builder()
            .longOpt("execute")
            .desc("Execute the following command")
            .hasArg(true)
            .numberOfArgs(1)
            .build());

        options.addOption(Option.builder()
            .longOpt("from")
            .desc("The directory which you want to run against. By default this is the current directory.")
            .hasArg(true)
            .numberOfArgs(1)
            .build());

        options.addOption(Option.builder("d")
            .longOpt("debug")
            .desc("Enable debug logging.")
            .build());

        options.addOption(Option.builder()
            .longOpt("ivy-log-info")
            .desc("Enable Ivy\'s logging at info.")
            .build());

        options
            .addOption(Option.builder()
                .longOpt("ivy-log-debug")
                .desc("Enable Ivy\'s logging at debug.")
                .build());

        options.addOption(Option.builder()
            .longOpt("silent")
            .desc("Disable all logging (except error).")
            .build());

        options.addOption(Option.builder()
            .longOpt("publisher")
            .argName("publisherClass")
            .hasArg()
            .numberOfArgs(1)
            .desc("Override the default publisher with this. Must be a fully qualified class like: "
                + TaskInjector.DEFAULT_PUBLISHER.getName())
            .build());

        options.addOption(Option.builder()
            .longOpt("exclude")
            .numberOfArgs(Option.UNLIMITED_VALUES)
            .valueSeparator(',')
            .desc("Used to exclude options from the env collection or command executors.")
            .build());

        options.addOption(Option.builder("h")
            .longOpt("help")
            .desc("Show usage information.")
            .build());
    }

}
