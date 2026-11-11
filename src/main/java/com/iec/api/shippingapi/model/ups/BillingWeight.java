package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
public class BillingWeight {

    private UnitOfMeasurement UnitOfMeasurement;
    private String Weight;

}
