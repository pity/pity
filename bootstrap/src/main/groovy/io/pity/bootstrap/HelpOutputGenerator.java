package io.pity.bootstrap;

import com.google.inject.Inject;
import io.pity.api.PropertyValueProvider;
import io.pity.api.environment.EnvironmentCollector;
import io.pity.api.execution.CommandExecutor;
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class HelpOutputGenerator {

    private final Set<CommandExecutor> commandExecutors;
    private final Set<EnvironmentCollector> environmentCollectors;
    private final PropertyValueProvider propertyValueProvider;

    @Inject
    HelpOutputGenerator(Set<CommandExecutor> commandExecutors, Set<EnvironmentCollector> environmentCollectors, PropertyValueProvider propertyValueProvider) {
        this.commandExecutors = commandExecutors;
        this.environmentCollectors = environmentCollectors;
        this.propertyValueProvider = propertyValueProvider;
    }

    public String getHelpOutput(CliArgumentProviderImpl cliArgumentProvider) {
        StringBuilder usageBuilder = new StringBuilder();
        usageBuilder.append(cliArgumentProvider.usage());

        usageBuilder.append(String.format("\nPity Version: %s\n", propertyValueProvider.getProperty("ask.version")));
        appendClasspath(usageBuilder);
        appendCollectorData(usageBuilder, CommandExecutor.class.getSimpleName(), commandExecutors);
        appendCollectorData(usageBuilder, EnvironmentCollector.class.getSimpleName(), environmentCollectors);

        return usageBuilder.toString();
    }

    private void appendClasspath(StringBuilder usageBuilder) {
        String property = System.getProperty("java.class.path");
        Set<String> urls = new TreeSet<String>();
        for (String path : property.split(":")) {
            File url = new File(path);
            urls.add(url.getName());
        }

        usageBuilder.append(String.format("Classpath: %s\n", StringUtils.join(urls, ", ")));
    }

    private void appendCollectorData(StringBuilder usageBuilder, String name, Set objects) {
        usageBuilder.append("\n" + name + "'s Available\n");
        for (Object object : objects) {
            usageBuilder.append(String.format("    %s\n", object.getClass().getName()));
        }
    }


}
