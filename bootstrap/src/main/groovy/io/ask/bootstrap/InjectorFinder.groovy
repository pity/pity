package io.ask.bootstrap
import com.google.inject.AbstractModule
import groovy.util.logging.Slf4j
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.util.ConfigurationBuilder

import java.util.regex.Pattern

@Slf4j
class InjectorFinder {

    def findInjectors() {
        def reflections = new Reflections(ConfigurationBuilder.build().addScanners(new ResourcesScanner()))
        def resources = reflections.getResources(Pattern.compile('.*\\.properties'))
        def injectorClasses = resources
                .findAll { it =~ /META-INF\/ask-plugins\// }
                .collect { log.debug("Found plugin property {}", it); return '/' + it }
                .collect { this.getClass().getResource(it).text }
                .collect { getInjectorClass(it) }
                .findAll { null != it }

        return injectorClasses
    }

    public AbstractModule getInjectorClass(String propertyFile) {
        def injector = propertyFile.split('\n').find { line ->
            return line =~ /^injector-class=.*/
        }

        if (injector) {
            def injectorClass = Class.forName(injector.replace('injector-class=', ''))
            if (AbstractModule.class.isAssignableFrom(injectorClass)) {
                log.debug("Found injector: {}", injectorClass.getName())
                return injectorClass.newInstance() as AbstractModule
            }
        }

        return null
    }
}
