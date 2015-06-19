package io.ask.bootstrap


import com.google.inject.Injector
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.api.reporting.CollectionResults
import io.ask.api.reporting.ReportPublisher
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

        publish(injectorFinder, injector)
    }

    private static void publish(PropertyFinder injectorFinder, Injector injector) {
        def collectionResults = injector.getInstance(CollectionResults)
        Class publisher = Class.forName(injectorFinder.createPropertyValueProvider().getProperty('default.publisher'))
        if (!ReportPublisher.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to XML", publisher)
            publisher = XmlReportPublisher.class
        }

        log.debug("Publisher class: {}", publisher.getName())
        (injector.getInstance(publisher) as ReportPublisher).publishReport(collectionResults)
    }
}
