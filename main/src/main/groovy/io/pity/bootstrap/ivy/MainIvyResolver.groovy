package io.pity.bootstrap.ivy


import groovy.transform.CompileStatic
import io.pity.bootstrap.provider.CliArgumentProviderImpl
import io.pity.bootstrap.injection.PropertyFinder

@CompileStatic
class MainIvyResolver {
    PropertyFinder injectorFinder;
    CliArgumentProviderImpl cliArgumentProvider

    MainIvyResolver(PropertyFinder injectorFinder, CliArgumentProviderImpl cliArgumentProvider) {
        this.injectorFinder = injectorFinder
        this.cliArgumentProvider = cliArgumentProvider;
    }

    void resolveDependencies(URLClassLoader rootLoader) {
        def dependencyResolver = new DependencyResolver(
            injectorFinder,
            cliArgumentProvider.ivyConfiguration, rootLoader)

        dependencyResolver.resolveDependencies()
    }
}
