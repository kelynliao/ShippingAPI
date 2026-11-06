package com.iec.api.shippingapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PackageInfo(
        @NotNull(message = "Shipping weight is required")
        @Positive(message = "Shipping weight must be greater than zero")
        BigDecimal shippingWeight,
        @PositiveOrZero(message = "Shipping insurance must not be negative")
        float shippingInsurance,
        @Positive(message = "Package length must be greater than zero")
        int dimensionLength,
        @Positive(message = "Package width must be greater than zero")
        int dimensionWidth,
        @Positive(message = "Package height must be greater than zero")
        int dimensionHeight,
        @Positive(message = "Group package count must be greater than zero")
        int groupPackageCount,
        @NotBlank(message = "Currency is required")
        String currency,
        @NotBlank(message = "Weight units are required")
        String weightUnits,
        @NotBlank(message = "Dimension units are required")
        String dimensionUnits) {
}
