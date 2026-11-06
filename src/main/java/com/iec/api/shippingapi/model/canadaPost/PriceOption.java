package com.iec.api.shippingapi.model.canadaPost;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.dataformat.xml.annotation.*;
import java.math.BigDecimal;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="option-code" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="option-name" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="option-price" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="qualifier" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="step-size" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="step-cost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="included-amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="max-value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="included" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="percent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceOption {

    @JacksonXmlProperty(localName = "option-code", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String optionCode;
    @JacksonXmlProperty(localName = "option-name", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected String optionName;
    @JacksonXmlProperty(localName = "option-price", namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected BigDecimal optionPrice;
    @JacksonXmlProperty(namespace = "http://www.canadapost.ca/ws/ship/rate-v4")
    protected OptionQualifier qualifier;

}
