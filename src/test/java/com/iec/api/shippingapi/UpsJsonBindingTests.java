package com.iec.api.shippingapi;

import com.iec.api.shippingapi.model.ups.AccessToken;
import com.iec.api.shippingapi.model.ups.RateRequest;
import com.iec.api.shippingapi.model.ups.RateRequestRoot;
import com.iec.api.shippingapi.model.ups.RateResponseRoot;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.*;

class UpsJsonBindingTests {
    private final JsonMapper mapper = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    @Test
    void writesUpsRequestPropertyNames() {
        var root = new RateRequestRoot();
        root.setRateRequest(new RateRequest());
        var json = mapper.readTree(mapper.writeValueAsString(root));
        assertTrue(json.has("RateRequest"));
        assertTrue(json.get("RateRequest").has("Shipment"));
    }

    @Test
    void readsUpsRatesAndOAuthToken() {
        var response = mapper.readValue("""
                {"RateResponse":{"RatedShipment":[{"Service":{"Code":"11"},
                "TotalCharges":{"CurrencyCode":"CAD","MonetaryValue":"12.34"}}]}}
                """, RateResponseRoot.class);
        assertEquals("12.34", response.getRateResponse().getRatedShipment().getFirst()
                .getTotalCharges().getMonetaryValue());
        var token = mapper.readValue("{\"access_token\":\"test-token\"}", AccessToken.class);
        assertEquals("test-token", token.getAccessToken());
    }
}
