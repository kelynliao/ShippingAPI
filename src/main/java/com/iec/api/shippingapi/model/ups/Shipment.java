package com.iec.api.shippingapi.model.ups;


import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Shipment {

    private Ship ShipFrom;
    private Shipper Shipper;
    private Ship ShipTo;
    private PaymentDetails PaymentDetails;
    private Package Package;

}
