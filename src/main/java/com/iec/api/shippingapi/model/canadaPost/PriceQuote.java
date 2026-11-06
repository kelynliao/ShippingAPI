package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceQuote {

    @JacksonXmlProperty(localName = "service-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String serviceCode;
    @JacksonXmlProperty(localName = "service-link", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected Link serviceLink;
    @JacksonXmlProperty(localName = "service-name", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String serviceName;
    @JacksonXmlProperty(localName = "price-details", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected PriceDetails priceDetails;
    @JacksonXmlProperty(localName = "weight-details", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected WeightDetails weightDetails;
    @JacksonXmlProperty(localName = "service-standard", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected ServiceStandard serviceStandard;

}
