package io.pity.tasks.util.process
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
                .start()

        def output = new ByteArrayOutputStream()
        def error = new ByteArrayOutputStream()
        def errorThread = process.consumeProcessErrorStream(error)
        def outputThread = process.consumeProcessOutputStream(output)

        process.waitForOrKill(timeout)
        errorThread.join(1000)
        outputThread.join(1000)

        return new ProcessResult(process, new ByteArrayInputStream(output.toByteArray()), new ByteArrayInputStream(error.toByteArray()))
    }
}
