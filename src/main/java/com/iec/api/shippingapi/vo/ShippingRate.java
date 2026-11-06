package com.iec.api.shippingapi.vo;

import java.math.BigDecimal;

public record ShippingRate(
        int id,
        String carrier,
        String serviceName,
        String serviceCode,
        BigDecimal price,
        String deliveryDay) {
}
