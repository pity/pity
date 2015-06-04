package io.ask.api;

import java.io.File;

/**
 * This class is used to inject the working directory of where the processes was executed. This exists because a
 * someone is able to change where the working directory is via the commandline
 */
public interface WorkingDirectoryProvider {

    /**
     * @return File pointing to the working directory
     */
    File getWorkingDirectory();
}
