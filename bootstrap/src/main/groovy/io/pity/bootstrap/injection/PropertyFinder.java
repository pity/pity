package io.pity.bootstrap.injection;

import io.pity.api.PropertyValueProvider;
import io.pity.bootstrap.provider.PropertyValueProviderImpl;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class PropertyFinder {

    public static final Logger logger = LoggerFactory.getLogger(PropertyFinder.class);
    private final Reflections reflections;
    List<Properties> propertiesList;

    public PropertyFinder() {
        ConfigurationBuilder configuration = ConfigurationBuilder.build()
            .addScanners(new ResourcesScanner())
            .setUrls(ClasspathHelper.forPackage("META-INF/pity-plugins"));
        reflections = new Reflections(configuration);
    }

    public List<Properties> findAskProperties() throws IOException {
        if(null == propertiesList) {
            propertiesList = new ArrayList<Properties>();
            Set<String> resources = reflections.getResources(Pattern.compile(".*\\.properties"));

            for (String resource : resources) {
                logger.debug("Found plugin property {}", resource);
                propertiesList.add(urlToProperties(this.getClass().getResourceAsStream("/" + resource)));
            }
        }

        return propertiesList;
    }

    public PropertyValueProvider createPropertyValueProvider() throws IOException {
        return new PropertyValueProviderImpl(findAskProperties());
    }

    public Properties urlToProperties(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }
}
