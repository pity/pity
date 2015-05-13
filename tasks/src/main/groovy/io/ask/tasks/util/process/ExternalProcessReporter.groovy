package io.ask.tasks.util.process
import groovy.transform.TupleConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@TupleConstructor
class ExternalProcessReporter {

    static Logger logger = LoggerFactory.getLogger(ExternalProcessReporter.class)

    String command
    File workingDir
    int timeout

    ExternalProcessReporter(String command, File workingDir, int timeout = 5 * 60 * 1000) {
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

    class ProcessResult {
        public InputStreamReader inputStream;
        public InputStreamReader errorStream;
        public int exitCode;

        ProcessResult(Process process) {
            inputStream = new InputStreamReader(process.getInputStream())
            errorStream = new InputStreamReader(process.getErrorStream())
            exitCode = process.exitValue()
        }
    }
}
