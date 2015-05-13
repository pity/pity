package io.ask.bootstrap.provider;

import io.ask.api.WorkingDirectoryProvider;

import java.io.File;

public class WorkingDirectoryProviderImpl implements WorkingDirectoryProvider {

    private final File workingDirectory;

    public WorkingDirectoryProviderImpl(File workingDirectory){
        this.workingDirectory = workingDirectory;
    }

    @Override
    public File getWorkingDirectory() {
        return workingDirectory;
    }
}
