package io.pity.api.reporting;

import io.pity.api.StopExecutionException;


public interface ReportPublisher {
    void publishReport(CollectionResults collectedResults);
    void validateRequirements() throws StopExecutionException;
}
