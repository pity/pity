package io.ask.tasks.util.process
import groovy.transform.TupleConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@TupleConstructor
class ExternalProcessReporterImpl implements ExternalProcessReporter {

    static Logger logger = LoggerFactory.getLogger(ExternalProcessReporterImpl.class)

    String command
    File workingDir
    int timeout

    ExternalProcessReporterImpl(String command, File workingDir, int timeout = 5 * 60 * 1000) {
        this.command = command
        this.workingDir = workingDir
        this.timeout = timeout
    }

    ProcessResult getResult() {
        logger.debug("String process `{}` with PWD: `{}`", command, workingDir)
        def process = new ProcessBuilder(command.split(' '))
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start()

        process.waitForOrKill(timeout)

        return new ProcessResult(process)
    }
}
