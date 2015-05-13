package io.ask.tasks.util
import groovy.transform.TupleConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@TupleConstructor
class ExternalProcessReporter {

    Logger logger = LoggerFactory.getLogger(ExternalProcessReporter.class)

    String command
    File workingDir
    Integer timeout = 5 * 60 * 1000

    ExternalProcessReporter(String command, File workingDir) {
        this.command = command
        this.workingDir = workingDir
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
