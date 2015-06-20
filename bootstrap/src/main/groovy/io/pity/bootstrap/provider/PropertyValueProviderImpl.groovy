package io.pity.bootstrap.provider

import io.pity.api.PropertyValueProvider

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


    String getProperty(String name){
        if(loadedProperties[name]) {
            return loadedProperties[name]
        } else {
            System.getProperty(name)
        }
    }
}
