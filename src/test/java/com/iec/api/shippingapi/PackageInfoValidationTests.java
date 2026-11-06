package com.iec.api.shippingapi;

import com.iec.api.shippingapi.dto.PackageInfo;
import com.iec.api.shippingapi.dto.ShippingAddress;
import com.iec.api.shippingapi.dto.ShippingRateRequest;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageInfoValidationTests {
    @Test
    void acceptsPositiveMeasurementsAndZeroInsurance() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var parcel = new PackageInfo(BigDecimal.ONE, 0, 10, 20, 30, 1, "CAD", "kg", "cm");
            assertTrue(factory.getValidator().validate(parcel).isEmpty());
        }
    }

    @Test
    void rejectsInvalidFieldsThroughShippingRateRequest() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var address = new ShippingAddress(null, null, null, null, null, null, null, null, null, null, null);
            var parcel = new PackageInfo(null, -1, 0, -1, 0, 0, null, "", " ");
            var violations = factory.getValidator().validate(new ShippingRateRequest(address, address, parcel));
            var paths = violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
            assertEquals(Set.of("packageInfo.shippingWeight", "packageInfo.shippingInsurance",
                    "packageInfo.dimensionLength", "packageInfo.dimensionWidth", "packageInfo.dimensionHeight",
                    "packageInfo.groupPackageCount", "packageInfo.currency", "packageInfo.weightUnits",
                    "packageInfo.dimensionUnits"), paths);

            for (var weight : new BigDecimal[] {BigDecimal.ZERO, BigDecimal.ONE.negate()}) {
                var invalidWeight = new PackageInfo(weight, 0, 10, 20, 30, 1, "CAD", "kg", "cm");
                assertEquals("shippingWeight", factory.getValidator().validate(invalidWeight)
                        .iterator().next().getPropertyPath().toString());
            }
        }
    }
}
