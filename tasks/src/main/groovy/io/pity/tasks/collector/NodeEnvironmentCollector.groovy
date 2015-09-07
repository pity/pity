package io.pity.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.transform.CompileStatic
import io.pity.api.WorkingDirectoryProvider
import io.pity.tasks.util.process.ExternalProcessCreator

@CompileStatic
class NodeEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    @Inject
    NodeEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                             Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(NodeEnvironmentCollector.class, workingDirectoryProvider, externalProcessCreatorProvider)
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
