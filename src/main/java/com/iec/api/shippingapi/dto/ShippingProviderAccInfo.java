package com.iec.api.shippingapi.dto;

public record ShippingProviderAccInfo(
        String name,
        String carrierApiUrlProduction,
        String carrierApiUrlSandbox,
        String apiUrl,
        String config,
        int shippingProviderId) {
}
