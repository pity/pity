package io.pity.tasks.collector

import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.environment.EnvironmentCollector
import io.pity.api.WorkingDirectoryProvider

@CompileStatic
class EnvironmentVariableCollector extends EnvironmentCollector {

    final Map<String, String> envVariables;

    @Inject
    EnvironmentVariableCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        this(workingDirectoryProvider, System.getenv())
    }

    EnvironmentVariableCollector(WorkingDirectoryProvider workingDirectoryProvider, Map<String, String> env) {
        super(workingDirectoryProvider)
        this.envVariables = env;
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        envVariables
                .findAll { key, value -> !key.startsWith('_') }
                .each { key, value -> environmentDataBuilder.addData(key, value) }
    }
}
