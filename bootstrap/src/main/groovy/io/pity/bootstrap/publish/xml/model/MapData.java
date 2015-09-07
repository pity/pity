package io.pity.bootstrap.publish.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@JacksonXmlRootElement
public class MapData {
    @JacksonXmlProperty(isAttribute = true)
    String name;

    @JacksonXmlText
    String value;
    public MapData(Map.Entry<String, ?> entry) {
        this.name = entry.getKey();
        if(null == entry.getValue()) {
            value = null;
        } else {
            this.value = StringUtils.trimToEmpty(entry.getValue().toString()).replace((char) 27, '^');
        }
    }
}
