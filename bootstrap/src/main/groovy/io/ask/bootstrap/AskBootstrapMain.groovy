package io.ask.bootstrap
import com.google.inject.AbstractModule
import com.google.inject.Guice
import io.ask.bootstrap.environment.BootstrapEnvironmentCollector
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.util.ConfigurationBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Pattern

class AskBootstrapMain {

    private static final Logger logger = LoggerFactory.getLogger(AskBootstrapMain.class)

    public static void main(String[] args) {
        def reflections = new Reflections(ConfigurationBuilder.build().addScanners(new ResourcesScanner()))
        def resources = reflections.getResources(Pattern.compile('.*\\.properties'))
        def injectorClasses = resources
                .findAll { it =~ /META-INF\/ask-plugins\// }
                .collect { logger.debug("Found plugin property {}", it); return '/' + it }
                .collect { this.getClass().getResource(it).text }
                .collect { getInjectorClass(it) }
                .findAll { null != it }

        def allInjectors = [new BootstrapInjector()] as List<AbstractModule>
        allInjectors.addAll(injectorClasses)

        def injector = Guice.createInjector(allInjectors)
        injector.getInstance(BootstrapEnvironmentCollector).collectEnvironmentData(new File('')).each {
            println it
        }

    }

    public static AbstractModule getInjectorClass(String propertyFile) {
        def injector = propertyFile.split('\n').find { line ->
            return line =~ /^injector-class=.*/
        }

        if (injector) {
            def injectorClass = Class.forName(injector.replace('injector-class=', ''))
            if (AbstractModule.class.isAssignableFrom(injectorClass)) {
                logger.debug("Found injector: {}", injectorClass.getName())
                return injectorClass.newInstance() as AbstractModule
            }
        }

        return null

    }
}
