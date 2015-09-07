package io.pity.gradle.plugin.version
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.Person
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.StopActionException
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.VerificationTask

class VersionBumpTask extends DefaultTask {

    public static final Logger logger = Logging.getLogger(VerificationTask)

    @InputFile
    File versionFile

    @Input
    Version currentVersion

    VersionBumpTask() {
        group = 'Version'
        description = 'Bump the version to the next version, message can contain [no bump], [ci skip], [bump minor], [bump major]'
    }

    @TaskAction
    public void bumpVersion() {
        def repo = Grgit.open(project.getProjectDir())
        def message = repo.head().fullMessage

        if (message.contains('[no bump]') || message.contains('[ci skip]') || currentVersion.isSnapshot()) {
            logger.lifecycle("Skipping bump...")
            throw new StopActionException('No bump should happen');
        }

        logger.info("Using message: {}", message)

        Version nextVersion;
        if (message.contains('[bump minor]')) {
            nextVersion = currentVersion.withNextMinor()
        } else if (message.contains('[bump major]')) {
            nextVersion = currentVersion.withNextMajor()
        } else {
            nextVersion = currentVersion.withNextPatch()
        }

        repo.tag.add(name: "v${nextVersion.toString()}")

        VersionFile.writeVersionToFile(versionFile, nextVersion)
        repo.add(patterns: ['version.properties'])
        repo.commit(message: "Bumping version to ${nextVersion.toString()}\n\n[ci skip]", author: new Person('circleci', 'ci@pity.io'))
        repo.push()
        repo.push(tags: true)
        getLogger().lifecycle("Next version is: {}", nextVersion.toString())
    }

}
