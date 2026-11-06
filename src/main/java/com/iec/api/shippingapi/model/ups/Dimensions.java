package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Dimensions {

    private UnitOfMeasurement UnitOfMeasurement;
    private String Length;
    private String Height;
    private String Width;

}
