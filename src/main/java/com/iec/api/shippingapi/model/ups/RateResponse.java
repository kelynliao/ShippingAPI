package com.iec.api.shippingapi.model.ups;

import lombok.Data;

import java.util.List;

@Data
public class RateResponse {

    private Response Response;
    private List<RatedShipment> RatedShipment;

}
