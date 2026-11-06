package com.iec.api.shippingapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "carrier-account.ups")
public record UpsAccount(
        String carrier,
        String endPoint,
        String clientId,
        String clientSecret,
        String accountNumber
)
 {}