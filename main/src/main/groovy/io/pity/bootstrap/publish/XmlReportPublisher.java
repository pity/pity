package io.pity.bootstrap.publish;

import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ReportPublisher;
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
