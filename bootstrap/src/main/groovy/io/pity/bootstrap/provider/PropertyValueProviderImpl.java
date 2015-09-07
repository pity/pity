package io.pity.bootstrap.provider;

import io.pity.api.PropertyValueProvider;
import org.codehaus.groovy.runtime.StringGroovyMethods;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Used to hold all the property files loaded via the property files.
 */
public class PropertyValueProviderImpl implements PropertyValueProvider {

    private Map<String, String> loadedProperties;

    public PropertyValueProviderImpl(List<Properties> propertiesList) {
        loadedProperties = new LinkedHashMap<>();

        for (Properties it : propertiesList) {
            Properties properties = new Properties();
            properties.putAll(it);
            properties.remove("injector-class");
            properties.forEach((key, value) -> loadedProperties.put((String)key, (String)value));
        }
    }

    /**
     * Loads the properties from the property files loaded from {@code META-INF/pity-plugins}
     *
     * @param name property name
     * @return the values loaded from pity-plugins, will fallback to {@link System#getProperty(String)}
     */
    public String getProperty(String name) {
        if (StringGroovyMethods.asBoolean(loadedProperties.get(name))) {
            return loadedProperties.get(name);
        } else {
            return System.getProperty(name);
        }

    }
}
