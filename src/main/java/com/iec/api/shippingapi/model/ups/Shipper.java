package com.iec.api.shippingapi.model.ups;


import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Shipper {
    private Address Address;
    private String ShipperNumber;
    private String Name;

}
