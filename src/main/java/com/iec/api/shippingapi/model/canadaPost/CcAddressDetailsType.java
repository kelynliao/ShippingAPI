package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CcAddressDetailsType {

    @JacksonXmlProperty(localName = "address-line-1", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String addressLine1;
    @JacksonXmlProperty(localName = "address-line-2", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String addressLine2;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String city;
    @JacksonXmlProperty(localName = "prov-state", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String provState;
    @JacksonXmlProperty(localName = "postal-zip-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String postalZipCode;
    @JacksonXmlProperty(localName = "country-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String countryCode;
}
