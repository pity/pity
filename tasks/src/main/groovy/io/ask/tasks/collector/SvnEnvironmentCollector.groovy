package io.ask.tasks.collector
import com.google.inject.Inject
import io.ask.api.EnvironmentCollector
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.ExternalProcessReporter
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SvnEnvironmentCollector extends EnvironmentCollector {

    public static final Logger logger = LoggerFactory.getLogger(SvnEnvironmentCollector.class)
    public static final String WORKING_COPY = 'is not a working copy'

    @Inject
    def SvnEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
    }

    @Override
    boolean shouldCollect() {
        def result = new ExternalProcessReporter('svn status --depth=empty', workingDirectory).getResult()
        if(result.inputStream.text.contains(WORKING_COPY) || result.errorStream.text.contains(WORKING_COPY)){
            return false;
        }

        return result.exitCode == 0
    }

    @Override
    void collect() {
        collectSvnCommandResults('svn status --depth=empty', 'status')
        collectSvnCommandResults('svn diff', 'diff')
    }

    private void collectSvnCommandResults(String command, String name) {
        def (diffOutput, diffErrorOutput) = new ExternalProcessReporter(command, workingDirectory).getResult()
        environmentDataBuilder.addData(name, diffOutput.text)

        def errorOutput = diffErrorOutput.text
        if (!StringUtils.isEmpty(errorOutput)) {
            logger.error("Error getting {} log: {}", name, diffErrorOutput.text)
        }
    }
}
