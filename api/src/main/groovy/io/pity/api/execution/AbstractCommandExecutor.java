package io.pity.api.execution;

import com.google.inject.Inject;
import io.pity.api.WorkingDirectoryProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;


abstract public class AbstractCommandExecutor implements CommandExecutor {

    final WorkingDirectoryProvider workingDirectoryProvider;
    final protected File tempDir;
    public final CommandExecutionResultBuilder commandExecutionResultBuilder;

    @Inject
    public AbstractCommandExecutor(WorkingDirectoryProvider workingDirectoryProvider) throws IOException {
        this.workingDirectoryProvider = workingDirectoryProvider;
        tempDir = new File(FileUtils.getTempDirectory(), "ask_" + RandomStringUtils.randomAlphanumeric(10));
        FileUtils.forceMkdir(tempDir);

        commandExecutionResultBuilder = new CommandExecutionResultBuilder(this.getClass().getSimpleName(), tempDir);
    }

    public File getWorkingDirectory() {
        return workingDirectoryProvider.getWorkingDirectory();
    }
}
