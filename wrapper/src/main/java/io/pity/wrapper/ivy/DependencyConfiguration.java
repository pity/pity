package io.pity.wrapper.ivy;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DependencyConfiguration {
    private URL configurationFile;
    private File cacheDir;
    private List<Dependency> dependencies = new ArrayList<Dependency>();

    public DependencyConfiguration(URL configurationFile, File cacheDir, List<Dependency> dependencies) {
        this.configurationFile = configurationFile;
        this.cacheDir = cacheDir;
        this.dependencies = dependencies;
    }

    public DependencyConfiguration(IvyConfiguration ivyConfiguration, File cacheDir) {
        this.cacheDir = cacheDir;
        this.configurationFile = ivyConfiguration.getIvySettingUrl();
        this.dependencies = ivyConfiguration.getDependencies();
    }

    public boolean shouldResolve() {
        return null != dependencies && !dependencies.isEmpty();
    }

    public URL getConfigurationFile() {
        return configurationFile;
    }

    public File getCacheDir() {
        return cacheDir;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
