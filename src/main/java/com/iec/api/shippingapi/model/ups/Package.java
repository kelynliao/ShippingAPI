package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Package {

    private Dimensions Dimensions;
    private PackageWeight PackageWeight;
    private PackagingType PackagingType;

}
