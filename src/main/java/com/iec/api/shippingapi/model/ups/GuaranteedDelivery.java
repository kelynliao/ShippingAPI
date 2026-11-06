package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
public class GuaranteedDelivery {

    private String DeliveryByTime;
    private String BusinessDaysInTransit;

}
