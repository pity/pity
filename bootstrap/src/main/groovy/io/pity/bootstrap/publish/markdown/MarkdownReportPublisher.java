package io.pity.bootstrap.publish.markdown;

import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ReportPublisher;

import java.io.File;


public class MarkdownReportPublisher implements ReportPublisher {
    @Override
    public void publishReport(CollectionResults collectedResults) {
        MarkdownCreator markdownCreator = new MarkdownCreator(collectedResults);
        markdownCreator.createMarkdown(new File("pity.markdown"));
    }

    @Override
    public void validateRequirements() {
        //NOOP
    }
}
