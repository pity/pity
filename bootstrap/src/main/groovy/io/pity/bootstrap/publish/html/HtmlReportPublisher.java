package io.pity.bootstrap.publish.html;

import io.pity.api.StopExecutionException;
import io.pity.api.reporting.CollectionResults;
import io.pity.api.reporting.ReportPublisher;
import io.pity.bootstrap.publish.markdown.MarkdownCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class HtmlReportPublisher implements ReportPublisher {

    public static final Logger log = LoggerFactory.getLogger(HtmlReportPublisher.class);

    @Override
    public void publishReport(CollectionResults collectedResults) {
        MarkdownCreator markdownCreator = new MarkdownCreator(collectedResults);
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

        String body = processor.markdownToHtml(markdownCreator.createMarkdown());
        try {
            FileUtils.write(new File("pity.html"), createHtmlPage(body));
        } catch (IOException e) {
            log.error("Unable to write html file", e);
        }
    }

    private String createHtmlPage(String body) throws IOException {
        InputStream templateStream = this.getClass().getResourceAsStream("/template.html");
        String template = IOUtils.toString(templateStream);
        return template.replace("__BODY__", body);
    }

    @Override
    public void validateRequirements() throws StopExecutionException {
        //NOOP
    }
}
