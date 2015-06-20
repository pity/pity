package io.pity.tasks.execution.gradle
import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.pity.api.WorkingDirectoryProvider
import io.pity.api.execution.AbstractCommandExecutor
import io.pity.api.execution.CommandExecutionResult
import io.pity.api.preprocess.CommandOptions
import org.apache.commons.io.output.TeeOutputStream
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProgressEvent
import org.gradle.tooling.ProgressListener

@Slf4j
class GradleCommandExecutor extends AbstractCommandExecutor {

    @Inject
    def GradleCommandExecutor(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
    }

    @Override
    int commandPrecedence() {
        return Integer.MIN_VALUE + 1
    }

    @Override
    boolean shouldStopExecutionAfter() {
        return true
    }

    @Override
    boolean willDoWork(CommandOptions commandOptions) {
        return commandOptions.getCommand().contains('gradle')
    }

    @Override
    CommandExecutionResult execute(CommandOptions commandOptions) {
        commandExecutionResultBuilder.setCommandOptions(commandOptions)

        log.info("About to execute gradle build. Please wait...")
        def projectConnection = GradleConnector.newConnector().forProjectDirectory(getWorkingDirectory()).connect()

        def stdOutStream = new ByteArrayOutputStream()
        def stdOutFile = new FileOutputStream(new File(tempDir, 'stdout.log'))
        def stdOutReader = new TeeOutputStream(stdOutFile, stdOutStream)

        def stdErrStream = new ByteArrayOutputStream()
        def stdErrFile = new FileOutputStream(new File(tempDir, 'stderr.log'))
        def stdErrReader = new TeeOutputStream(stdErrFile, stdErrStream)

        def progressListener = new GradleProgressListener();

        def buildLauncher = projectConnection
            .newBuild()
            .withArguments(commandOptions.getArguments() as String[])
            .setStandardOutput(stdOutReader)
            .setStandardError(stdErrReader)
            .addProgressListener(progressListener)

        try {
            buildLauncher.run()
        } catch (Exception e){
            commandExecutionResultBuilder.setException(e)
            log.error("Caught exception", e)
        }

        projectConnection.close()

        println progressListener.statusEvents
        commandExecutionResultBuilder.setOutput(stdOutStream.toString(), stdErrStream.toString())
        return commandExecutionResultBuilder.build()
    }

    @Slf4j
    static class GradleProgressListener implements ProgressListener {

        def statusEvents = []

        @Override
        void statusChanged(ProgressEvent event) {
            statusEvents << [System.currentTimeMillis(), event.getDescription()]
            log.trace("Got status message: `{}`", event.getDescription())
        }
    }
}
