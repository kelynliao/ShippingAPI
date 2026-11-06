package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;

import java.math.BigDecimal;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxType {

    @JacksonXmlText
    protected BigDecimal value;
    @JacksonXmlProperty(isAttribute = true, localName = "percent")
    protected BigDecimal percent;
}
