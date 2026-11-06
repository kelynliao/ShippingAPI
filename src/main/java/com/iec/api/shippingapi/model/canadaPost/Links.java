package com.iec.api.shippingapi.model.canadaPost;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "links", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
public class Links {

    @JacksonXmlProperty(localName = "link", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<Link> links = new ArrayList<>();

}
