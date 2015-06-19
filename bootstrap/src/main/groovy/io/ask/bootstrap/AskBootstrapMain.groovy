package io.ask.bootstrap


import com.google.inject.Injector
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.ask.api.PropertyValueProvider
import io.ask.api.StopExecutionException
import io.ask.api.reporting.CollectionResults
import io.ask.api.reporting.ReportPublisher
import io.ask.bootstrap.injection.MainInjectorCreator
import io.ask.bootstrap.injection.PropertyFinder
import io.ask.bootstrap.ivy.MainIvyResolver
import io.ask.bootstrap.provider.CliArgumentProviderImpl
import io.ask.bootstrap.publish.XmlReportPublisher

@Slf4j
@CompileStatic
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

        def publisher = findPublisher(injectorFinder, injector)
        try {
            publisher.validateRequirements()
        } catch (StopExecutionException e) {
            log.error("Stopping execution because {}", e.getMessage())
            return;
        }

        injector.getInstance(RootExecutor).executeAll()

        publisher.publishReport(injector.getInstance(CollectionResults))
    }

    private static ReportPublisher findPublisher(PropertyFinder injectorFinder, Injector injector) {
        Class publisher = Class.forName(injectorFinder.createPropertyValueProvider().getProperty('default.publisher'))
        if (!ReportPublisher.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to XML", publisher)
            publisher = XmlReportPublisher.class
        }

        log.debug("Publisher class: {}", publisher.getName())
        return (injector.getInstance(publisher) as ReportPublisher)
    }
}
