package io.ask.api.reporting;

public interface ReportPublisher {
    void publishReport(CollectionResults collectedResults);
    void validateRequirements();
}
