package com.iec.api.shippingapi.model.canadaPost;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.dataformat.xml.annotation.*;
import java.math.BigDecimal;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Option {

    @JacksonXmlProperty(localName = "option-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String optionCode;
    @JacksonXmlProperty(localName = "option-amount", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected BigDecimal optionAmount;

}
