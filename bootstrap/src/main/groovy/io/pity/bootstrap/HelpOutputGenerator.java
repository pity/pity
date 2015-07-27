package io.pity.bootstrap;

import com.google.inject.Inject;
import io.pity.api.PropertyValueProvider;
import io.pity.api.environment.EnvironmentCollector;
import io.pity.api.execution.CommandExecutor;
import io.pity.bootstrap.provider.container.CommandExecutorContainer;
import io.pity.bootstrap.provider.container.EnvironmentCollectorContainer;
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl;
import io.pity.bootstrap.provider.container.FilteringContainer;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Used to generate the help output.
 */
public class HelpOutputGenerator {

    private final CommandExecutorContainer commandContainer;
    private final EnvironmentCollectorContainer environmentContainer;
    private final PropertyValueProvider propertyValueProvider;

    @Inject
    HelpOutputGenerator(CommandExecutorContainer commandExecutorContainer,
                        EnvironmentCollectorContainer environmentCollectorContainer,
                        PropertyValueProvider propertyValueProvider) {
        this.commandContainer = commandExecutorContainer;
        this.environmentContainer = environmentCollectorContainer;
        this.propertyValueProvider = propertyValueProvider;
    }

    public String getHelpOutput(CliArgumentProviderImpl cliArgumentProvider) throws IOException {
        StringBuilder usageBuilder = new StringBuilder();
        usageBuilder.append(cliArgumentProvider.usage());

        usageBuilder.append(String.format("\nPity Version: %s\n", propertyValueProvider.getProperty("pity.version")));
        appendClasspath(usageBuilder);

        appendCollectorData(usageBuilder, CommandExecutor.class.getSimpleName(), commandContainer);
        appendCollectorData(usageBuilder, EnvironmentCollector.class.getSimpleName(), environmentContainer);

        return usageBuilder.toString();
    }

    /**
     * Appends the classpath to the help output
     * @param usageBuilder Builder that should be used to add the help contents to
     */
    private void appendClasspath(StringBuilder usageBuilder) {
        String property = System.getProperty("java.class.path");
        Set<String> urls = new TreeSet<String>();
        for (String path : property.split(":")) {
            File url = new File(path);
            urls.add(url.getName());
        }

        usageBuilder.append(String.format("Classpath: %s\n", StringUtils.join(urls, ", ")));
    }

    /**
     * Used to append {@link FilteringContainer} to the help output
     *
     * @param usageBuilder Where the output should be added to
     * @param name title used for the {@link FilteringContainer}
     * @param objects the {@link FilteringContainer}
     * @throws IOException
     */
    private void appendCollectorData(StringBuilder usageBuilder, String name, FilteringContainer objects) throws IOException {
        usageBuilder.append("\n").append(name).append("'s Available\n");
        for (Object object : objects.getAvailable()) {
            usageBuilder.append(String.format("    %s\n", object.getClass().getName()));
        }

        for (Object object : objects.getFiltered()) {
            usageBuilder.append(String.format("    %s - (excluded)\n", object.getClass().getName()));
        }
    }


}
