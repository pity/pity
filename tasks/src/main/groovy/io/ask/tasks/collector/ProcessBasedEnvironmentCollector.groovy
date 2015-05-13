package io.ask.tasks.collector

import io.ask.api.EnvironmentCollector
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.ExternalProcessReporter
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class ProcessBasedEnvironmentCollector extends EnvironmentCollector {

    public final Logger logger = LoggerFactory.getLogger(this.getClass())

    public ProcessBasedEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
    }

    public void collectCommandResults(String resultKey, String command) {
        def processResult = new ExternalProcessReporter(command, workingDirectory).getResult()
        environmentDataBuilder.addData(resultKey, processResult.inputStream.text)

        def errorOutput = processResult.errorStream.text
        if (!StringUtils.isEmpty(errorOutput)) {
            logger.error("Error getting {} log: {}", resultKey, processResult.errorStream.text)
        }
    }
}
