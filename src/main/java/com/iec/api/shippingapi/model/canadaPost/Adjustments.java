package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Adjustments {

    @JacksonXmlProperty(localName = "adjustment", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    @JacksonXmlElementWrapper(useWrapping = false)
    protected List<Adjustment> adjustments = new ArrayList<>();

}
