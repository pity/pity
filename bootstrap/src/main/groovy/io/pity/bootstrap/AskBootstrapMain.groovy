package io.pity.bootstrap


import com.google.inject.Injector
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.pity.api.PropertyValueProvider
import io.pity.api.StopExecutionException
import io.pity.api.reporting.CollectionResults
import io.pity.api.reporting.ReportPublisher
import io.pity.bootstrap.injection.MainInjectorCreator
import io.pity.bootstrap.injection.PropertyFinder
import io.pity.bootstrap.ivy.MainIvyResolver
import io.pity.bootstrap.provider.CliArgumentProviderImpl
import io.pity.bootstrap.publish.XmlReportPublisher
import org.apache.commons.lang3.StringUtils

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

        new MainIvyResolver(injectorFinder, cliArgumentProvider).resolveDependencies(ClassLoader.getSystemClassLoader() as URLClassLoader)

        //Reload with new classes
        injectorFinder = new PropertyFinder()

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
        def publisherName = injectorFinder.createPropertyValueProvider().getProperty('default.publisher')
        if (StringUtils.isEmpty(publisherName)) {
            publisherName = XmlReportPublisher.class.getName()
        }
        Class publisher = Class.forName(publisherName)
        if (!ReportPublisher.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to XML", publisher)
            publisher = XmlReportPublisher.class
        }

        log.debug("Publisher class: {}", publisher.getName())
        return (injector.getInstance(publisher) as ReportPublisher)
    }
}