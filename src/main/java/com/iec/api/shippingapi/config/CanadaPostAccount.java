package com.iec.api.shippingapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "carrier-account.canada-post")
public record CanadaPostAccount(
        String carrier,
        String username,
        String password,
        String customerNumber,
        String endpoint) {
}
