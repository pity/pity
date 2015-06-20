package io.ask.bootstrap.ivy


import groovy.transform.CompileStatic
import io.ask.bootstrap.provider.CliArgumentProviderImpl
import io.ask.bootstrap.injection.PropertyFinder
import org.codehaus.groovy.tools.RootLoader

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
