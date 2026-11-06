package com.iec.api.shippingapi.model.ups;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessToken {
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("issued_at")
    private String issuedAt;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_count")
    private String refreshCount;
    @JsonProperty("status")
    private String status;
}
