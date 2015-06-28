package io.pity.bootstrap.publish


import com.google.inject.Injector
import groovy.util.logging.Slf4j
import io.pity.api.StopExecutionException
import io.pity.api.reporting.CollectionResults
import io.pity.api.reporting.ReportPublisher
import io.pity.bootstrap.injection.PropertyFinder
import io.pity.bootstrap.provider.cli.InternalCliArgumentProvider
import org.apache.commons.lang3.StringUtils

@Slf4j
class PublishManager {
    final ReportPublisher reportPublisher;

    PublishManager(InternalCliArgumentProvider cliArgumentProvider, PropertyFinder injectorFinder, Injector injector) {
        reportPublisher = getReportPublisher(cliArgumentProvider, injectorFinder, injector)
    }

    static ReportPublisher getReportPublisher(InternalCliArgumentProvider cliArgumentProvider, PropertyFinder injectorFinder, Injector injector) {
        String publisherName = findReportPublisher(injectorFinder)
        if(cliArgumentProvider.isOverridePublisher()) {
            publisherName = cliArgumentProvider.overriddenPublisher
            log.info("Overriding publisher to {}", publisherName)
        }

        Class publisher = Class.forName(publisherName)
        if (!ReportPublisher.isAssignableFrom(publisher)) {
            log.error("Unable to publish results using {}, failing back to XML", publisher)
            publisher = XmlReportPublisher.class
        }

        log.debug("Publisher class: {}", publisher.getName())
        return (injector.getInstance(publisher) as ReportPublisher)
    }

    private static String findReportPublisher(PropertyFinder injectorFinder) {
        def publisherName = injectorFinder.createPropertyValueProvider().getProperty('default.publisher')
        if (StringUtils.isEmpty(publisherName)) {
            publisherName = XmlReportPublisher.class.getName()
            log.trace("Could not find publisher, default to {}", publisherName)
        }
        return publisherName;
    }

    public boolean shouldExecutionContinue(){
        try {
            reportPublisher.validateRequirements()
            return true;
        } catch (StopExecutionException e) {
            log.error("Stopping execution because {}", e.getMessage())
            return false;
        }
    }

    void publishReport(CollectionResults collectionResults) {
        reportPublisher.publishReport(collectionResults)
    }
}
