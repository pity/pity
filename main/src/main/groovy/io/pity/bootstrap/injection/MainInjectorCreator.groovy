package io.pity.bootstrap.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.pity.bootstrap.provider.CliArgumentProviderImpl;


public class MainInjectorCreator {

    private final CliArgumentProviderImpl cliArgumentProvider;
    private final PropertyFinder injectorFinder;

    MainInjectorCreator(CliArgumentProviderImpl cliArgumentProvider, PropertyFinder injectorFinder){
        this.cliArgumentProvider = cliArgumentProvider;
        this.injectorFinder = injectorFinder;
    }

    public Injector getInjector() {
        def allInjectors = [] as List<AbstractModule >
            allInjectors.add(new BootstrapInjector(injectorFinder.createPropertyValueProvider(), cliArgumentProvider))
        allInjectors.addAll(injectorFinder.findInjectors())

        Guice.createInjector()
        def injector = Guice.createInjector(allInjectors)
        return injector
    }
}
