package io.pity.api.environment;

import com.google.inject.Inject;
import io.pity.api.WorkingDirectoryProvider;

import java.io.File;

public abstract class EnvironmentCollector {

    public final WorkingDirectoryProvider workingDirectoryProvider;
    public final EnvironmentDataBuilder environmentDataBuilder;

    @Inject
    public EnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider){
        this.environmentDataBuilder = EnvironmentDataBuilder.Builder(this.getClass().getSimpleName());
        this.workingDirectoryProvider = workingDirectoryProvider;
    }

    final public File getWorkingDirectory() {
        return workingDirectoryProvider.getWorkingDirectory();
    }

    public abstract boolean shouldCollect();
    public abstract void collect();

    final public EnvironmentData collectEnvironmentData() {

        if(shouldCollect()){
            collect();
        }
        return environmentDataBuilder.build();
    }
}
