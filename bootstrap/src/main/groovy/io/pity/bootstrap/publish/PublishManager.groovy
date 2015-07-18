package io.pity.bootstrap.publish
import groovy.util.logging.Slf4j
import io.pity.api.StopExecutionException
import io.pity.api.reporting.CollectionResults
import io.pity.api.reporting.ReportPublisher

@Slf4j
class PublishManager {
    final ReportPublisher reportPublisher;

    PublishManager(ReportPublisher reportPublisher) {
        this.reportPublisher = reportPublisher;
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
