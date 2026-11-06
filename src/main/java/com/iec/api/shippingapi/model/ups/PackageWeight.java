package com.iec.api.shippingapi.model.ups;


import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class PackageWeight {
    private UnitOfMeasurement UnitOfMeasurement;
    private String Weight;

}
