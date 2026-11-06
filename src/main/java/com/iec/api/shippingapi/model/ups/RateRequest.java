package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class RateRequest {

    private Request Request;
    private Shipment Shipment;

}
