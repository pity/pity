package io.ask.api.reporting;

import io.ask.api.StopExecutionException;


public interface ReportPublisher {
    void publishReport(CollectionResults collectedResults);
    void validateRequirements() throws StopExecutionException;
}
