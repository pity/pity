package io.ask.tasks.collector

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.transform.CompileStatic
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator
import org.ajoberstar.grgit.BranchStatus
import org.ajoberstar.grgit.exception.GrgitException
import org.ajoberstar.grgit.operation.OpenOp
import org.apache.commons.io.filefilter.FileFileFilter
import org.eclipse.jgit.lib.RepositoryBuilder

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
