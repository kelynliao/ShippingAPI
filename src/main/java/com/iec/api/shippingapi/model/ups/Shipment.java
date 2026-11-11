package com.iec.api.shippingapi.model.ups;


import lombok.Data;

@Data
public class Shipment {
    private Ship ShipFrom;
    private Shipper Shipper;
    private Ship ShipTo;
    private PaymentInformation paymentInformation;
    private Package Package;
}
