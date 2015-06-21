package io.pity.api;

/**
 * {@link PropertyValueProvider} will load in any property files located in /META-INF/pity-plugins and provide the
 * properties accessible by {@link PropertyValueProvider#getProperty(String)}. If multiple property files have the same
 * key then there is no guarantee to which will be used.
 */
public interface PropertyValueProvider {

    /**
     * Get a property from the loaded property files
     * @param name property name
     * @return property value.
     */
    String getProperty(String name);
}
