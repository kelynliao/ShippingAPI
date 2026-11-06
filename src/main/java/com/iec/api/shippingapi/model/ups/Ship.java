package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Ship {
    private Address Address;
    private String Name;

}
