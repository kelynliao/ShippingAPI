package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "mailing-scenario", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
public class MailingScenario {

    @JacksonXmlProperty(localName = "customer-number", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String customerNumber;
    @JacksonXmlProperty(localName = "contract-id", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String contractId;
    @JacksonXmlProperty(localName = "promo-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String promoCode;
    @JacksonXmlProperty(localName = "quote-type", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String quoteType;
    @JacksonXmlProperty(localName = "expected-mailing-date", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    @JsonSerialize(using = XmlDateBinding.Serializer.class)
    @JsonDeserialize(using = XmlDateBinding.Deserializer.class)
    protected XMLGregorianCalendar expectedMailingDate;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected Options options;
    @JacksonXmlProperty(localName = "parcel-characteristics", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected ParcelCharacteristics parcelCharacteristics;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected Services services;
    @JacksonXmlProperty(localName = "origin-postal-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String originPostalCode;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected Destination destination;
}
