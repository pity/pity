package io.pity.tasks.collector

import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.environment.EnvironmentCollector
import io.pity.api.WorkingDirectoryProvider

@CompileStatic
class WorkingDirectoryEnvironmentCollector extends EnvironmentCollector {

    public static final String WORKING_DIRECTORY = 'workingDirectory'
    private final WorkingDirectoryProvider workingDirectoryProvider

    @Inject
    WorkingDirectoryEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(WorkingDirectoryEnvironmentCollector.class)
        this.workingDirectoryProvider = workingDirectoryProvider
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        environmentDataBuilder.addData(WORKING_DIRECTORY, workingDirectoryProvider.getWorkingDirectory().getAbsolutePath())
    }
}
