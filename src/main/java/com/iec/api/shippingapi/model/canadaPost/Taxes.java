package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Taxes {

    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected TaxType gst;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected TaxType pst;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected TaxType hst;

}
