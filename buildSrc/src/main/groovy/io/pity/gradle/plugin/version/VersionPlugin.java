package io.pity.gradle.plugin.version;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.File;

public class VersionPlugin implements Plugin<Project> {

    Logger logger = Logging.getLogger(VersionPlugin.class);

    @Override
    public void apply(Project target) {
        if(target.getRootProject() != target) {
            throw new GradleException("Cannot apply dependency plugin to a non-root project");
        }

        File versionProperties = target.file("version.properties");

        Version version = VersionFile.getVersion(versionProperties).withNextPatch();

        if(!target.hasProperty("release") || !Boolean.parseBoolean((String) target.property("release"))) {
            version = version.asSnapshot();
        }

        logger.lifecycle("Version {}", version);
        target.allprojects(new VersionAction(version));

        VersionBumpTask versionBump = target.getTasks().create("versionBump", VersionBumpTask.class);
        versionBump.setVersionFile(versionProperties);
        versionBump.setCurrentVersion(version);
    }

    private static class VersionAction implements Action<Project> {
        private final Version version;

        public VersionAction(Version version) {
            this.version = version;
        }

        @Override
        public void execute(Project project) {
            project.setVersion(version);
        }
    }
}
