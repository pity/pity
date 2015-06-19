package io.ask.bootstrap.ivy


import io.ask.bootstrap.provider.CliArgumentProviderImpl
import io.ask.bootstrap.injection.PropertyFinder
import org.codehaus.groovy.tools.RootLoader

class MainIvyResolver {
    PropertyFinder injectorFinder;
    CliArgumentProviderImpl cliArgumentProvider

    MainIvyResolver(PropertyFinder injectorFinder, CliArgumentProviderImpl cliArgumentProvider) {
        this.injectorFinder = injectorFinder
        this.cliArgumentProvider = cliArgumentProvider;
    }

    void resolveDependencies() {
        def dependencyResolver = new DependencyResolver(
            injectorFinder,
            cliArgumentProvider.ivyConfiguration,
            this.getClass().getClassLoader().rootLoader as RootLoader)

        dependencyResolver.resolveDependencies()
    }
}
