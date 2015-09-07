package io.pity.bootstrap.publish;

import io.pity.api.StopExecutionException;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ReportPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishManager {
    public static final Logger logger = LoggerFactory.getLogger(PublishManager.class);

    private final ReportPublisher reportPublisher;

    public PublishManager(ReportPublisher reportPublisher) {
        this.reportPublisher = reportPublisher;
    }

    public boolean shouldExecutionContinue() {
        try {
            reportPublisher.validateRequirements();
            return true;
        } catch (StopExecutionException e) {
            logger.error("Stopping execution because {}", e.getMessage());
            return false;
        }

    }

    public void publishReport(CollectionResults collectionResults) {
        reportPublisher.publishReport(collectionResults);
    }
}
