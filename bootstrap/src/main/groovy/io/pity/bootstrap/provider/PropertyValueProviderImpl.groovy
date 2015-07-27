package io.pity.bootstrap.provider

import io.pity.api.PropertyValueProvider

/**
 * Used to hold all the property files loaded via the property files.
 */
class PropertyValueProviderImpl implements PropertyValueProvider {
    Map<String, String> loadedProperties

    PropertyValueProviderImpl(List<Properties> propertiesList){
        loadedProperties = [:]

        for(Properties it : propertiesList) {
            def properties = new Properties()
            properties.putAll(it)
            properties.remove('injector-class')
            loadedProperties.putAll(properties as Map<String, String>)
        }
    }

    /**
     * Loads the properties from the property files loaded from {@code META-INF/pity-plugins}
     * @param name property name
     * @return the values loaded from pity-plugins, will fallback to {@link java.lang.System#getProperty(java.lang.String)}
     */
    String getProperty(String name){
        if(loadedProperties[name]) {
            return loadedProperties[name]
        } else {
            System.getProperty(name)
        }
    }
}
