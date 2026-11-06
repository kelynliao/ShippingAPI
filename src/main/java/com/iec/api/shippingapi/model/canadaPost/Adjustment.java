package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;
import java.math.BigDecimal;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Adjustment {

    @JacksonXmlProperty(localName = "adjustment-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String adjustmentCode;
    @JacksonXmlProperty(localName = "adjustment-name", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String adjustmentName;
    @JacksonXmlProperty(localName = "adjustment-cost", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected BigDecimal adjustmentCost;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected AdjustmentQualifier qualifier;

}
