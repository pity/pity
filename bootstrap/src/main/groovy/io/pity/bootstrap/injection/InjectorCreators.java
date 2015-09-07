package io.pity.bootstrap.injection;

import com.google.inject.AbstractModule;
import groovy.util.logging.Slf4j;
import io.pity.api.PropertyValueProvider;
import io.pity.bootstrap.injection.injectors.InitializationInjector;
import io.pity.bootstrap.injection.injectors.TaskInjector;
import io.pity.bootstrap.provider.cli.CliArgumentProviderImpl;
import io.pity.bootstrap.publish.PublisherAbstractModule;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class InjectorCreators {

    private static final Logger logger = LoggerFactory.getLogger(InjectorCreators.class);

    private final PropertyFinder propertyFinder = new PropertyFinder();

    public TaskInjector findTaskInjectors(CliArgumentProviderImpl cliArgumentProvider) {
        List<AbstractModule> allInjectors = new ArrayList<>();
        PropertyValueProvider propertyValueProvider = propertyFinder.createPropertyValueProvider();

        allInjectors.add(new TaskRootInjector(propertyValueProvider, cliArgumentProvider));
        allInjectors.add(new PublisherAbstractModule(propertyValueProvider, cliArgumentProvider));
        allInjectors.addAll(findInjectors("task-injector-class"));

        return new TaskInjector(allInjectors);
    }

    public InitializationInjector createInitializationInjector() {
        return new InitializationInjector(findInjectors("bootstrap-injector-class"));
    }

    public PropertyFinder getPropertyFinder() {
        return propertyFinder;
    }

    public List<AbstractModule> findInjectors(final String propertyName) {
        return propertyFinder.findAskProperties().stream()
            .map(properties -> getInjectorClass(properties, propertyName))
            .filter(prop -> null != prop)
            .collect(Collectors.toList());
    }

    public AbstractModule getInjectorClass(Properties properties, String propertyName) {
        String injector = properties.getProperty(propertyName);

        try {
            if (StringGroovyMethods.asBoolean(injector)) {
                Class<?> injectorClass = Class.forName(injector);
                if (AbstractModule.class.isAssignableFrom(injectorClass)) {
                    logger.debug("Found injector: {}", injectorClass.getName());
                    return (AbstractModule)injectorClass.newInstance();
                }

            }
        } catch (IllegalAccessException|InstantiationException|ClassNotFoundException e) {
            throw new RuntimeException("Failure to get injectors", e);
        }
        return null;
    }
}
