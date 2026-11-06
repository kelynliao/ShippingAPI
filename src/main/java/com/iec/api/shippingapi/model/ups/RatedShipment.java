package com.iec.api.shippingapi.model.ups;

import lombok.Data;

import java.util.List;

@Data
public class RatedShipment {

    private BillingWeight BillingWeight;
    private TotalCharges TotalCharges;
    private List<RatedShipmentAlert> RatedShipmentAlert;
    private ServiceOptionsCharges ServiceOptionsCharges;
    private Service Service;
    private TransportationCharges TransportationCharges;
    private RatedPackage RatedPackage;

}
