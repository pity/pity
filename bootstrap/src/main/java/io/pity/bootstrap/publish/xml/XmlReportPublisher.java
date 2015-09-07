package io.pity.bootstrap.publish.xml;

import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ReportPublisher;
import io.pity.bootstrap.publish.xml.model.RootResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class XmlReportPublisher implements ReportPublisher {

    public static final Logger logger = LoggerFactory.getLogger(XmlReportPublisher.class);

    @Override
    public void publishReport(CollectionResults collectedResults) {
        try {
            XmlFactory xmlFactory = new XmlFactory(new WstxInputFactory(), new WstxOutputFactory());
            XmlMapper xmlMapper = new XmlMapper(xmlFactory);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(new File("generated-data.xml"), new RootResults(collectedResults));
        } catch (IOException e) {
            logger.error("Unable to write report", e);
            throw new RuntimeException("Unable to write report", e);
        }
    }

    @Override
    public void validateRequirements() {
        //NOOP
    }
}
