package io.ask.bootstrap.publish;

import io.ask.api.reporting.CollectionResults;
import io.ask.api.reporting.ReportPublisher;
import java.io.File;


public class XmlReportPublisher implements ReportPublisher {
    @Override
    public void publishReport(CollectionResults collectedResults) {
        XmlOutputGenerator xmlOutputGenerator = new XmlOutputGenerator(collectedResults);
        xmlOutputGenerator.writeToFile(new File("generated-data.xml"));
    }

    @Override
    public void validateRequirements() {
        //NOOP
    }
}
