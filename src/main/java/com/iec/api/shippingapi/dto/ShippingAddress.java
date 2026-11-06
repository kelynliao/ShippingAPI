package com.iec.api.shippingapi.dto;

public record ShippingAddress(
        String firstName,
        String lastName,
        String address1,
        String address2,
        String company,
        String city,
        String province,
        String country,
        String countryCode,
        String postalCode,
        String phoneNumber) {
}
