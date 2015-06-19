package io.ask.tasks.collector

import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.ask.api.environment.EnvironmentCollector
import io.ask.api.WorkingDirectoryProvider

@CompileStatic
class WorkingDirectoryEnvironmentCollector extends EnvironmentCollector {

    public static final String WORKING_DIRECTORY = 'workingDirectory'

    @Inject
    WorkingDirectoryEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
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
