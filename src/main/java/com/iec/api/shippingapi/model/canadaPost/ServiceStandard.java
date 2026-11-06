package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.annotation.JsonDeserialize;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceStandard {

    @JacksonXmlProperty(localName = "am-delivery", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected boolean amDelivery;
    @JacksonXmlProperty(localName = "guaranteed-delivery", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected boolean guaranteedDelivery;
    @JacksonXmlProperty(localName = "expected-transit-time", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected Integer expectedTransitTime;
    @JacksonXmlProperty(localName = "expected-delivery-date", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    @JsonSerialize(using = XmlDateBinding.Serializer.class)
    @JsonDeserialize(using = XmlDateBinding.Deserializer.class)
    protected XMLGregorianCalendar expectedDeliveryDate;

}
