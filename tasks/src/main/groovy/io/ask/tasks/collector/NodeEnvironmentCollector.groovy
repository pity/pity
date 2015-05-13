package io.ask.tasks.collector

import com.google.inject.Inject
import io.ask.api.WorkingDirectoryProvider

class NodeEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    @Inject
    NodeEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
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
