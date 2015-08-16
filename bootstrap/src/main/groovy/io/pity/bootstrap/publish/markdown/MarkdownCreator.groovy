package io.pity.bootstrap.publish.markdown
import io.pity.api.environment.EnvironmentData
import io.pity.api.execution.CommandExecutionResult
import io.pity.api.reporting.CollectionResults
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class MarkdownCreator {

    public static final Logger logger = LoggerFactory.getLogger(MarkdownCreator.class);

    private final CollectionResults collectionResults;

    public MarkdownCreator(CollectionResults collectionResults) {
        this.collectionResults = collectionResults;
    }

    public void createMarkdown(File destinationFile) {
        def parent = destinationFile.getAbsoluteFile().getParentFile()
        if(!parent.exists()) {
            parent.mkdirs()
        }
        destinationFile.text = createMarkdown()
    }

    public String createMarkdown() {
        StringBuilder sb = new StringBuilder();

        collectionResults.commandExecutionResults.sort { it.commandExecutorClass }.each {
            createCommandExecutionResults(sb, it)
        }

        collectionResults.environmentData.sort { it.collectorName }.each {
            createEnvironmentResults(sb, it)
        }

        return sb.toString();
    }

    static private void createEnvironmentResults(StringBuilder sb, EnvironmentData environmentData) {
        sb.append("\n## ${environmentData.collectorName}\n")


        environmentData.environmentResults.each { key, value ->
            sb.append("\n### ${key}")

            def valueString = value.toString()
            if(StringUtils.isNotBlank(valueString)) {
                valueString.replaceAll("(?m)^", "    ")
                sb.append("\n").append(valueString.replaceAll("(?m)^", "    ")).append("\n")
            }
        }
    }

    static private void createCommandExecutionResults(StringBuilder sb, CommandExecutionResult it) {
        sb.append("\n## ${it.commandExecutorClass}\n")

        sb.append("\n### Command Executed\n")
        sb.append("```\n${it.commandExecuted.command} ${it.commandExecuted.arguments.join(' ')}\n```")

        sb.append("\n### Exception Thrown\n")
        sb.append("```\n${ExceptionUtils.getStackTrace(it.exceptionThrown)}\n```")

        sb.append("\n### Other Results\n")
        sb.append("```\n")
        it.otherResults.each { key, value ->
            sb.append("==> ${key}\n $value")
        }
        sb.append("\n```")

        sb.append("\n### Result Dir\n")
        sb.append("```\n==> ${it.resultDir.absolutePath}\n```")

        sb.append("### Standard Out\n```${it.stdOut}```")
        sb.append("### Standard Error\n```${it.stdError}```")
    }
}
