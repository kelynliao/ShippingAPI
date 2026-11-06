package com.iec.api.shippingapi.model.ups;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;

import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
//@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public final class Address {

    private List<String> AddressLine;
    private String StateProvinceCode;
    private String PostalCode;
    private String City;
    private String CountryCode;

}
