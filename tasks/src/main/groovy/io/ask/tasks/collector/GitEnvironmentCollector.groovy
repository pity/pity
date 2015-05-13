package io.ask.tasks.collector
import com.google.inject.Inject
import com.google.inject.Provider
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.process.ExternalProcessCreator
import org.ajoberstar.grgit.BranchStatus
import org.ajoberstar.grgit.exception.GrgitException
import org.ajoberstar.grgit.operation.OpenOp
import org.eclipse.jgit.lib.RepositoryBuilder

class GitEnvironmentCollector extends ProcessBasedEnvironmentCollector {

    File gitDir

    @Inject
    def GitEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider,
                                Provider<ExternalProcessCreator> externalProcessCreatorProvider) {
        super(workingDirectoryProvider, externalProcessCreatorProvider)
    }

    @Override
    boolean shouldCollect() {
        def repoBuilder = new RepositoryBuilder().findGitDir()
        gitDir = repoBuilder.getGitDir()
        return null != gitDir
    }

    @Override
    void collect() {

        logger.info("Generating working status of your project...")

        def grgit = new OpenOp(dir: gitDir).call()

        environmentDataBuilder.addData('head', grgit.head().id)
        try {
            def status = grgit.branch.status(name: grgit.branch.current.name) as BranchStatus
            environmentDataBuilder
                    .addData('ahead', status.aheadCount)
                    .addData('behind', status.behindCount)
                    .addData('tracking', grgit.branch.current?.trackingBranch?.fullName)

        } catch (GrgitException grgitException) {
            logger.trace("Error getting branches", grgitException)
        }

        collectCommandResults('patch', 'git diff HEAD origin/master')
        collectCommandResults('diff', 'git diff')
        collectCommandResults('reflog', 'git reflog')
    }
}
