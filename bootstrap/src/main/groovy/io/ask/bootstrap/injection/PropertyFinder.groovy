package io.ask.bootstrap.injection

import com.google.inject.AbstractModule
import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.bootstrap.provider.PropertyValueProviderImpl
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.util.ConfigurationBuilder

import java.util.regex.Pattern

@Slf4j
class PropertyFinder {

    List<AbstractModule> findInjectors() {
        def injectorClasses = findAskProperties()
            .collect { getInjectorClass(it) }
            .findAll { null != it }

        return injectorClasses
    }

    @Memoized
    List<Properties> findAskProperties() {
        def reflections = new Reflections(ConfigurationBuilder.build().addScanners(new ResourcesScanner()))
        def resources = reflections.getResources(Pattern.compile('.*\\.properties'))
        return resources
            .findAll { it =~ /META-INF\/ask-plugins\// }
            .collect { log.debug("Found plugin property {}", it); return '/' + it }
            .collect { this.getClass().getResource(it) }
            .collect { urlToProperties(it) }
    }

    @Memoized
    PropertyValueProvider createPropertyValueProvider() {
        return new PropertyValueProviderImpl(findAskProperties())
    }

    Properties urlToProperties(URL propertyUrl) {
        def properties = new Properties()
        properties.load(propertyUrl.newReader())
        return properties
    }

    public AbstractModule getInjectorClass(Properties properties) {
        def injector = properties.getProperty('injector-class')

        if (injector) {
            def injectorClass = Class.forName(injector)
            if (AbstractModule.class.isAssignableFrom(injectorClass)) {
                log.debug("Found injector: {}", injectorClass.getName())
                return injectorClass.newInstance() as AbstractModule
            }
        }

        return null
    }
}