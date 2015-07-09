package io.pity.bootstrap;

import com.google.inject.Inject;
import io.pity.api.environment.EnvironmentCollector;
import io.pity.api.execution.CommandExecutor;
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl;

import java.util.Set;

public class HelpOutputGenerator {

    private final Set<CommandExecutor> commandExecutors;
    private final Set<EnvironmentCollector> environmentCollectors;

    @Inject
    HelpOutputGenerator(Set<CommandExecutor> commandExecutors, Set<EnvironmentCollector> environmentCollectors){
        this.commandExecutors = commandExecutors;
        this.environmentCollectors = environmentCollectors;
    }

    public String getHelpOutput(CliArgumentProviderImpl cliArgumentProvider) {
        StringBuilder usageBuilder = new StringBuilder();
        usageBuilder.append(cliArgumentProvider.usage());

        appendData(usageBuilder, CommandExecutor.class.getSimpleName(), commandExecutors);
        appendData(usageBuilder, EnvironmentCollector.class.getSimpleName(), environmentCollectors);

        return usageBuilder.toString();
    }

    private void appendData(StringBuilder usageBuilder, String name, Set objects) {
        usageBuilder.append("\n" + name + "'s Available\n");
        for (Object object : objects) {
            usageBuilder.append(String.format("    %s\n", object.getClass().getName()));
        }
    }


}
