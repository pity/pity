package io.pity.bootstrap.injection
import com.google.inject.AbstractModule
import groovy.util.logging.Slf4j
import io.pity.bootstrap.injection.injectors.InitializationInjector
import io.pity.bootstrap.injection.injectors.TaskInjector
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl
import io.pity.bootstrap.publish.PublisherAbstractModule

@Slf4j
class InjectorCreators {

    final private PropertyFinder propertyFinder = new PropertyFinder()

    public TaskInjector findTaskInjectors(CliArgumentProviderImpl cliArgumentProvider) {
        def allInjectors = [] as List<AbstractModule >
        def propertyValueProvider = propertyFinder.createPropertyValueProvider()

        allInjectors.add(new TaskRootInjector(propertyValueProvider, cliArgumentProvider))
        allInjectors.add(new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider))
        allInjectors.addAll(findInjectors('task-injector-class'))

        return new TaskInjector(allInjectors)
    }

    public InitializationInjector createInitializationInjector() {
        return new InitializationInjector(findInjectors('bootstrap-injector-class'))
    }

    public PropertyFinder getPropertyFinder() {
        return propertyFinder;
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
