package io.ask.tasks.util.process;

import java.io.File;

public class ExternalProcessCreator {

    public ExternalProcessReporter createProcess(String command, File workingDir, int timeout) {
        return new ExternalProcessReporterImpl(command, workingDir, timeout);
    }

    public ExternalProcessReporter createProcess(String command, File workingDir) {
        return createProcess(command, workingDir, 5 * 60 * 1000);
    }
}
