package com.iec.api.shippingapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ShippingRateRequest(
        @NotNull(message = "Shipping destination address is required") @Valid
        ShippingAddress shippingToAddress,
        @NotNull(message = "Shipping origin address is required") @Valid
        ShippingAddress shippingFromAddress,
        @NotNull(message = "Package information is required") @Valid
        PackageInfo packageInfo) {
}
