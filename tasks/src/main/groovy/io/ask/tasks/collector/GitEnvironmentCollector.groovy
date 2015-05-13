package io.ask.tasks.collector
import com.google.inject.Inject
import io.ask.api.EnvironmentCollector
import io.ask.api.WorkingDirectoryProvider
import io.ask.tasks.util.ExternalProcessReporter
import org.ajoberstar.grgit.BranchStatus
import org.ajoberstar.grgit.exception.GrgitException
import org.ajoberstar.grgit.operation.OpenOp
import org.apache.commons.lang3.StringUtils
import org.eclipse.jgit.lib.RepositoryBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitEnvironmentCollector extends EnvironmentCollector {

    public static final Logger logger = LoggerFactory.getLogger(GitEnvironmentCollector.class)

    File gitDir

    @Inject
    def GitEnvironmentCollector(WorkingDirectoryProvider workingDirectoryProvider) {
        super(workingDirectoryProvider)
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

        collectGitCommandResults(workingDirectory, 'git diff HEAD origin/master', 'patch')
        collectGitCommandResults(workingDirectory, 'git diff', 'diff')
    }

    private void collectGitCommandResults(File workingDirectory, String command, String name) {
        def processResult = new ExternalProcessReporter(command, workingDirectory).getResult()
        environmentDataBuilder.addData(name, processResult.inputStream.text)
        def errorOutput = processResult.errorStream.text
        if (!StringUtils.isEmpty(errorOutput)) {
            logger.error("Error getting {} log: {}", name, processResult.errorStream.text)
        }
    }
}
