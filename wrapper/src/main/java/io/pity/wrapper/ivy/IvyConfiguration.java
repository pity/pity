package io.pity.wrapper.ivy;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IvyConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(IvyConfiguration.class);
    public static final String PITY_CONF_FILE = ".pity/config.properties";

    private PityProperties pityProperties;

    List<Dependency> dependencies;

    public IvyConfiguration() throws URISyntaxException, IOException {
        dependencies = new ArrayList<Dependency>();
        pityProperties = new PityProperties();
    }

    public void update() throws IOException {
        File homeDir = new File(System.getProperty("user.home"));
        File homePityConf = new File(homeDir, PITY_CONF_FILE);
        File currentDirConf = new File(PITY_CONF_FILE);

        if(currentDirConf.exists()) {
            updateSettings(currentDirConf);
        } else if(homePityConf.exists()) {
            updateSettings(homePityConf);
        }

        pityProperties.update(System.getProperties());

        addPityDependencies(pityProperties.version);

        if(null != pityProperties.dependencies) {
            String[] dependencyArray = pityProperties.dependencies.split(",");
            for (String dep : dependencyArray) {
                dependencies.add(new Dependency(dep));
            }
        }

        logger.debug("Version: {}", pityProperties.version);
        logger.debug("Dependencies: {}", pityProperties.dependencies);
        logger.debug("Ivy Settings: {}", pityProperties.ivySettings);
    }

    void updateSettings(File file) throws IOException {
        logger.debug("Loading properties from: {}", file);
        Properties props = new Properties();

        FileReader fileReader = new FileReader(file);
        props.load(fileReader);

        pityProperties.update(props);
    }

    void addPityDependencies(String version) {
        dependencies.add(new Dependency("io.pity", "bootstrap", version));
        dependencies.add(new Dependency("io.pity", "tasks", version));
        dependencies.add(new Dependency("io.pity", "api", version));
    }

    public URL getIvySettingUrl() {
        return pityProperties.ivySettings;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
