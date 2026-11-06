package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CcDetailsType {

    @JacksonXmlProperty(localName = "cc-type", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected CcType ccType;
    @JacksonXmlProperty(localName = "cc-number", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String ccNumber;
    @JacksonXmlProperty(localName = "cc-name", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String ccName;
    @JacksonXmlProperty(localName = "cc-expiry", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String ccExpiry;
    @JacksonXmlProperty(localName = "cc-cvv", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String ccCvv;
    @JacksonXmlProperty(localName = "cc-address-details", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected CcAddressDetailsType ccAddressDetails;
}
