package io.pity.bootstrap.publish.markdown;

import io.pity.api.environment.EnvironmentData;
import io.pity.api.execution.CommandExecutionResult;
import io.pity.api.reporting.CollectionResults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownCreator {

    public static final Logger logger = LoggerFactory.getLogger(MarkdownCreator.class);
    private final CollectionResults collectionResults;

    public MarkdownCreator(CollectionResults collectionResults) {
        this.collectionResults = collectionResults;
    }

    public void createMarkdown(File destinationFile) {
        File parent = destinationFile.getAbsoluteFile().getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            FileUtils.write(destinationFile, createMarkdown());
        } catch (IOException e) {
            throw new RuntimeException("Unable to write output", e);
        }
    }

    public String createMarkdown() {
        final StringBuilder sb = new StringBuilder();

        collectionResults.getCommandExecutionResults().stream()
            .sorted((o1, o2) -> o1.getCommandExecutorClass().compareTo(o2.getCommandExecutorClass()))
            .forEachOrdered(commandExecutionResult -> createCommandExecutionResults(sb, commandExecutionResult));

        collectionResults.getEnvironmentData().stream()
            .sorted((o1, o2) -> o1.getCollectorName().compareTo(o2.getCollectorName()))
            .forEachOrdered(environmentData -> createEnvironmentResults(sb, environmentData));

        return sb.toString();
    }

    private static void createEnvironmentResults(final StringBuilder sb, EnvironmentData environmentData) {
        sb.append("\n## ").append(environmentData.getCollectorName()).append("\n");

        environmentData.getEnvironmentResults().forEach((key, value) -> {
            sb.append("\n### ").append(key).append("\n");

            String valueString = value.toString();
            if (StringUtils.isNotBlank(valueString)) {
                valueString.replaceAll("(?m)^", "    ");
                sb.append("\n").append(valueString.replaceAll("(?m)^", "    ")).append("\n");
            }

        });
    }

    private static void createCommandExecutionResults(StringBuilder sb, CommandExecutionResult it) {
        addHeader(sb, "##", it.getCommandExecutorClass());

        addHeader(sb, "### Command Executed");
        addResult(sb, it.getCommandExecuted().getCommand(), DefaultGroovyMethods.join(it.getCommandExecuted().getArguments(), " "));

        addHeader(sb, "### Exception Thrown");
        addResult(sb, ExceptionUtils.getStackTrace(it.getExceptionThrown()));

        List<String> results = it.getOtherResults().entrySet().stream()
            .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
            .map(entry -> "==> " + entry.getKey() + "\n " + entry.getValue() + "\n")
            .collect(Collectors.toList());
        addHeader(sb, "### Other Results");
        addResult(sb, results);

        addHeader(sb, "### Result Dir");
        addResult(sb, "==>", it.getResultDir().getAbsolutePath());

        addHeader(sb, "### Standard Out");
        addResult(sb, it.getStdOut());

        addHeader(sb, "### Standard Out");
        addResult(sb, it.getStdError());
    }

    public static void addHeader(StringBuilder sb, String... values) {
        sb.append("\n");
        for (String value : values) {
            sb.append(value).append(" ");
        }
        sb.append("\n");
    }

    public static void addResult(StringBuilder sb, String... values) {
        addResult(sb, Arrays.asList(values));
    }

    public static void addResult(StringBuilder sb, List<String> values) {
        sb.append("\n```\n");
        for (String entry : values) {
            sb.append(entry).append(" ");
        }
        sb.append("\n```\n");
    }
}
