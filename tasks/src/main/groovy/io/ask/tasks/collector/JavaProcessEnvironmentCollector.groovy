package io.ask.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator

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
        collectCommandResults('processes', 'ps -few', { it.contains('java') })
    }
}
