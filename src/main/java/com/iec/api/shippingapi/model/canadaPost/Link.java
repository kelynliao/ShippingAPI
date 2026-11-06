package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;
import java.math.BigInteger;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "link", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
public class Link {

    @JacksonXmlProperty(isAttribute = true, localName = "href")
    protected String href;
    @JacksonXmlProperty(isAttribute = true, localName = "rel")
    protected String rel;
    @JacksonXmlProperty(isAttribute = true, localName = "index")
    protected BigInteger index;
    @JacksonXmlProperty(isAttribute = true, localName = "media-type")
    protected String mediaType;
}
