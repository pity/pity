package io.pity.wrapper.ivy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PityProperties {
    private static final Logger logger = LoggerFactory.getLogger(PityProperties.class);

    String version;
    String dependencies;
    URL ivySettings;

    PityProperties() throws IOException, URISyntaxException {
        defaultPityVersion();
        ivySettings = PityProperties.class.getResource("/pity/ivy/settings.xml").toURI().toURL();
    }

    private void defaultPityVersion() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/wrapper-default.properties");
        properties.load(inputStream);
        version = properties.getProperty("pity.wrapper.version");
    }

    public void update(Properties properties) throws MalformedURLException {
        if(null != properties.getProperty("pity.ivy.settings.file")) {
            ivySettings = new File(properties.getProperty("pity.ivy.settings.file")).toURI().toURL();
            logger.trace("Setting ivySettings to: {}", ivySettings);
        }

        if(null != properties.getProperty("pity.dependencies")) {
            dependencies = properties.getProperty("pity.dependencies");
            logger.trace("Setting dependencies to: {}", dependencies);
        }

        if(null != properties.getProperty("pity.version")) {
            version = properties.getProperty("pity.version");
            logger.trace("Setting version to: {}", version);
        }
    }
}
