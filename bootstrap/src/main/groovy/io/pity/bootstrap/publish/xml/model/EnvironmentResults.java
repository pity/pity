package io.pity.bootstrap.publish.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement
public class EnvironmentResults {
    @JacksonXmlProperty(isAttribute = true, localName = "name")
    String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "entry")
    List<MapData> entries;

    public EnvironmentResults(io.pity.api.environment.EnvironmentData environmentData) {
        this.name = environmentData.getCollectorName();
        this.entries = environmentData.getEnvironmentResults().entrySet()
            .stream().map(MapData::new)
            .collect(Collectors.toList());
    }
}
