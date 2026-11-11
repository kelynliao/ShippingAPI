package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class PaymentInformation {

    private ShipmentCharge ShipmentCharge;

}
