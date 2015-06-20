package io.pity.wrapper.ivy

class DependencyConfiguration {
    URL configurationFile
    File cacheDir
    List<Dependency> dependencies = []

    DependencyConfiguration(URL configurationFile, File cacheDir, List<Dependency> dependencies) {
        this.configurationFile = configurationFile
        this.cacheDir = cacheDir
        this.dependencies = dependencies
    }

    DependencyConfiguration(IvyConfiguration ivyConfiguration, File cacheDir) {
        this.cacheDir = cacheDir;
        this.configurationFile = ivyConfiguration.ivySettingUrl;
        this.dependencies = ivyConfiguration.dependencies;
    }

    boolean shouldResolve() {
        return null != dependencies && !dependencies.isEmpty()
    }
}
