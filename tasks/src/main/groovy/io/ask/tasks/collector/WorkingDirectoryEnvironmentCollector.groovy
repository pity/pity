package io.ask.tasks.collector
import io.ask.api.EnvironmentCollector
import io.ask.api.EnvironmentData
import io.ask.api.EnvironmentDataBuilder

class WorkingDirectoryEnvironmentCollector implements EnvironmentCollector{

    public static final String WORKING_DIRECTORY = 'workingDirectory'

    @Override
    EnvironmentData collect(File workingDirectory) {
        def builder = EnvironmentDataBuilder.Builder(WorkingDirectoryEnvironmentCollector.class.getSimpleName())
        builder.addData(WORKING_DIRECTORY, workingDirectory.getAbsolutePath())
        return builder.build()
    }
}
