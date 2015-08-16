package io.pity.tasks.collector
import com.google.inject.Provider
import io.pity.api.environment.EnvironmentCollector
import io.pity.api.WorkingDirectoryProvider
import io.pity.tasks.util.process.ExternalProcessCreator
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class ProcessBasedEnvironmentCollector extends EnvironmentCollector {

    public final Logger logger = LoggerFactory.getLogger(this.getClass())
    final Provider<ExternalProcessCreator> externalProcessCreatorProvider

    public ProcessBasedEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                                            Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider)
        this.externalProcessCreatorProvider = externalProcessCreatorProvider
    }

    public void collectCommandResults(String resultKey, String command, Closure filter) {
        def processResult = externalProcessCreatorProvider.get().createProcess(command, workingDirectory).getResult()
        StringWriter sw = new StringWriter()
        processResult.standardOut.filterLine(sw, filter)
        sw.close()
        environmentDataBuilder.addData(resultKey, sw.toString().trim())

        def errorOutput = processResult.errorStream.text
        if (!StringUtils.isEmpty(errorOutput)) {
            environmentDataBuilder.addData(resultKey + '-error', errorOutput.trim())
            logger.debug("Error getting {} log: {}", resultKey, errorOutput)
        }
    }

    public void collectCommandResults(String resultKey, String command) {
        collectCommandResults(resultKey, command, { true })
    }
}
