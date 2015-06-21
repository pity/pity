package io.pity.bootstrap.injection

import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import io.pity.api.PropertyValueProvider
import io.pity.bootstrap.provider.PropertyValueProviderImpl
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.util.ConfigurationBuilder

import java.util.regex.Pattern

@Slf4j
class PropertyFinder {

    @Memoized()
    List<Properties> findAskProperties() {
        def reflections = new Reflections(ConfigurationBuilder.build().addScanners(new ResourcesScanner()))
        def resources = reflections.getResources(Pattern.compile('.*\\.properties'))
        return resources
            .findAll { it =~ /META-INF\/pity-plugins\// }
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
}
