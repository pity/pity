package io.pity.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.transform.CompileStatic
import io.pity.api.WorkingDirectoryProvider
import io.pity.tasks.util.process.ExternalProcessCreator

@CompileStatic
class JavaProcessEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    @Inject
    JavaProcessEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                             Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        collectCommandResults('processes', 'ps -few', { String it -> it.contains('java') })
    }
}
