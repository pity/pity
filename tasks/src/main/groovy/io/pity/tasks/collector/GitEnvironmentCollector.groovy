package io.pity.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import io.pity.api.WorkingDirectoryProvider
import io.pity.tasks.util.process.ExternalProcessCreator

class GitEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    @Inject
    def GitEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                                Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
    }

    @Override
    boolean shouldCollect() {

        File dir = getWorkingDirectory()
        while(dir && dir.getAbsolutePath() != '/') {
            if ('.git' in dir.list()) {
                return true;
            }
            dir = dir.getParentFile()
        }
        return false;
    }

    @Override
    void collect() {

        logger.info("Generating working status of your project...")

        collectCommandResults('status', 'git status --short --branch')
        collectCommandResults('origin/master', 'git rev-parse origin/master')
        collectCommandResults('patch', 'git diff HEAD origin/master')
        collectCommandResults('diff', 'git diff')
        collectCommandResults('reflog', 'git reflog')
    }
}
