package io.ask.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.transform.CompileStatic
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator

@CompileStatic
class NodeEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    @Inject
    NodeEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                             Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        collectCommandResults('version', 'node --version')
    }
}
