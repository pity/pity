package io.pity.api.execution;

import com.google.inject.Inject;
import io.pity.api.WorkingDirectoryProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;


/**
 * All plugins that want to be able to execute a command <strong>should</strong> extend this. This is a convenience
 * implementation for {@link CommandExecutor}
 */
abstract public class AbstractCommandExecutor implements CommandExecutor {

    final WorkingDirectoryProvider workingDirectoryProvider;
    final protected File tempDir;
    public final CommandExecutionResultBuilder commandExecutionResultBuilder;

    @Inject
    public AbstractCommandExecutor(WorkingDirectoryProvider workingDirectoryProvider) throws IOException {
        this.workingDirectoryProvider = workingDirectoryProvider;
        tempDir = new File(FileUtils.getTempDirectory(), "pity_" + RandomStringUtils.randomAlphanumeric(10));
        FileUtils.forceMkdir(tempDir);

        commandExecutionResultBuilder = new CommandExecutionResultBuilder(this.getClass().getSimpleName(), tempDir);
    }

    /**
     * The working directory to use used to execute the process
     *
     * @return File to the working directory.
     */
    public File getWorkingDirectory() {
        return workingDirectoryProvider.getWorkingDirectory();
    }

    @Override
    public int compareTo(@Nullable CommandExecutor commandExecutor) {
        if(null == commandExecutor) {
            return 1;
        }
        return Integer.compare(commandPrecedence(), commandExecutor.commandPrecedence());
    }
}
