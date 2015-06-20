package io.pity.wrapper.ivy;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IvyConfiguration {

    public static final String PITY_CONF_FILE = ".pity/config.properties";

    URL ivySettingUrl;
    List<Dependency> dependencies;

    public IvyConfiguration() throws URISyntaxException, MalformedURLException {
        ivySettingUrl = this.getClass().getResource("/pity/ivy/settings.xml").toURI().toURL();
        dependencies = new ArrayList<Dependency>();
    }

    public void update() throws IOException {
        File homeDir = new File(System.getProperty("user.home"));
        File homePityConf = new File(homeDir, PITY_CONF_FILE);
        File currentDirConf = new File(PITY_CONF_FILE);

        if(currentDirConf.exists()) {
            updateSettings(currentDirConf);
        } else if(homePityConf.exists()) {
            updateSettings(homePityConf);
        } else {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getResourceAsStream("/wrapper-default.properties");
            properties.load(inputStream);
            String wrapperVersion = properties.getProperty("pity.wrapper.version");

            dependencies.add(new Dependency("io.pity", "bootstrap", wrapperVersion));
            dependencies.add(new Dependency("io.pity", "tasks", wrapperVersion));
            dependencies.add(new Dependency("io.pity", "api", wrapperVersion));
        }
    }

    void updateSettings(File file) throws IOException {
        Properties pityProperties = new Properties();

        FileReader fileReader = new FileReader(file);
        pityProperties.load(fileReader);

        ivySettingUrl = new File(pityProperties.getProperty("pity.ivy.settings.file")).toURI().toURL();
        String dependenciesString = pityProperties.getProperty("pity.dependencies");
        String version = pityProperties.getProperty("pity.version");
        dependencies.add(new Dependency("io.pity", "bootstrap", version));
        dependencies.add(new Dependency("io.pity", "api", version));
        if(null != dependenciesString) {
            String[] dependencyArray = dependenciesString.split(",");
            for (String dep : dependencyArray) {
                dependencies.add(new Dependency(dep));
            }
        }
    }

    public URL getIvySettingUrl() {
        return ivySettingUrl;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
