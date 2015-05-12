package io.ask.tasks.collector

import io.ask.api.EnvironmentCollector
import io.ask.api.EnvironmentData
import io.ask.api.EnvironmentDataBuilder
import io.ask.tasks.util.ExternalProcessReporter
import org.ajoberstar.grgit.BranchStatus
import org.ajoberstar.grgit.exception.GrgitException
import org.ajoberstar.grgit.operation.OpenOp
import org.apache.commons.lang3.StringUtils
import org.eclipse.jgit.lib.RepositoryBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitEnvironmentCollector implements EnvironmentCollector {

    public static final Logger logger = LoggerFactory.getLogger(GitEnvironmentCollector.class)
    def builder

    GitEnvironmentCollector() {
        builder = EnvironmentDataBuilder
                .Builder(GitEnvironmentCollector.class.getSimpleName())
                .addData('ahead', null)
                .addData('behind', null)
                .addData('tracking', null)
    }

    @Override
    EnvironmentData collect(File workingDirectory) {

        def repoBuilder = new RepositoryBuilder().findGitDir()
        if (!repoBuilder.getGitDir()) {
            return builder.build()
        }

        logger.info("Generating working status of your project...")

        def grgit = new OpenOp(dir: repoBuilder.getGitDir()).call()

        builder.addData('head', grgit.head().id)
        try {
            def status = grgit.branch.status(name: grgit.branch.current.name) as BranchStatus
            builder
                    .addData('ahead', status.aheadCount)
                    .addData('behind', status.behindCount)
                    .addData('tracking', grgit.branch.current?.trackingBranch?.fullName)

        } catch (GrgitException grgitException) {
            logger.trace("Error getting branches", grgitException)
        }

        collectGitCommandResults(workingDirectory, 'git diff HEAD origin/master', 'patch')
        collectGitCommandResults(workingDirectory, 'git diff', 'diff')

        return builder.build()
    }

    private void collectGitCommandResults(File workingDirectory, String command, String name) {
        def (diffOutput, diffErrorOutput) = new ExternalProcessReporter(command, workingDirectory).getResult()
        builder.addData(name, diffOutput.text)
        def errorOutput = diffErrorOutput.text
        if (!StringUtils.isEmpty(errorOutput)) {
            logger.error("Error getting {} log: {}", name, diffErrorOutput.text)
        }
    }
}
