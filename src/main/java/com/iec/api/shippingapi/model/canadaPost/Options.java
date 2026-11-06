package com.iec.api.shippingapi.model.canadaPost;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.dataformat.xml.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Options {

    @JacksonXmlProperty(localName = "option", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<Option> options = new ArrayList<>();

}
