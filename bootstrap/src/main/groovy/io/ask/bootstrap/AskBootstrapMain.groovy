package io.ask.bootstrap


import com.google.inject.Injector
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.api.reporting.CollectionResults
import io.ask.bootstrap.injection.MainInjectorCreator
import io.ask.bootstrap.injection.PropertyFinder
import io.ask.bootstrap.ivy.MainIvyResolver
import io.ask.bootstrap.provider.CliArgumentProviderImpl
import io.ask.bootstrap.publish.XmlReportPublisher

@Slf4j
class AskBootstrapMain {

    public static void main(String[] args) {
        def cliArgumentProvider = new CliArgumentProviderImpl(args)

        if (cliArgumentProvider.isHelp()) {
            println cliArgumentProvider.usage()
            return
        }

        PropertyFinder injectorFinder = new PropertyFinder()

        new MainIvyResolver(injectorFinder, cliArgumentProvider).resolveDependencies()

        Injector injector = new MainInjectorCreator(cliArgumentProvider, injectorFinder).getInjector();
        log.info("Loading version {}", injector.getInstance(PropertyValueProvider).getProperty('ask.version'))

        injector.getInstance(RootExecutor).executeAll()

        new XmlReportPublisher().publishReport(injector.getInstance(CollectionResults))
    }
}
