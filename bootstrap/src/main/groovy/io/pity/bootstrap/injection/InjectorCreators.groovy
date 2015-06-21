package io.pity.bootstrap.injection

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import groovy.util.logging.Slf4j
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl

@Slf4j
class InjectorCreators {

    private PropertyFinder propertyFinder = new PropertyFinder()

    public Injector findTaskInjectors(CliArgumentProviderImpl cliArgumentProvider) {
        def allInjectors = [] as List<AbstractModule >
        def bootstrapInjector = new TaskRootInjector(propertyFinder.createPropertyValueProvider(), cliArgumentProvider)
        allInjectors.add(bootstrapInjector)
        allInjectors.addAll(findInjectors('task-injector-class'))

        return Guice.createInjector(allInjectors)
    }

    public Injector findBootstrapInjectors() {
        return Guice.createInjector(findInjectors('bootstrap-injector-class'))
    }

    List<AbstractModule> findInjectors(String propertyName) {
        def injectorClasses = propertyFinder.findAskProperties()
            .collect { getInjectorClass(it, propertyName) }
            .findAll { null != it }

        return injectorClasses
    }

    AbstractModule getInjectorClass(Properties properties, String propertyName) {
        def injector = properties.getProperty(propertyName)

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
