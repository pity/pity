package io.pity.bootstrap.publish.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.pity.api.reporting.CollectionResults;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "results")
public class RootResults {

    public RootResults(CollectionResults collectedResults) {
        this.environment = collectedResults.getEnvironmentData().stream()
            .map(EnvironmentResults::new)
            .collect(Collectors.toList());

        this.execution = collectedResults.getCommandExecutionResults().stream()
            .map(ExecutionResults::new)
            .collect(Collectors.toList());
    }

    @JacksonXmlElementWrapper(localName = "collector")
    @JacksonXmlProperty(localName = "environment")
    List<EnvironmentResults> environment;

    List<Object> execution;
}
