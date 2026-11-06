package com.iec.api.shippingapi.model.canadaPost;

public enum ThirdPartyAccountCodeType {

    PB,
    FP,
    NE;

    public String value() {
        return name();
    }

    public static ThirdPartyAccountCodeType fromValue(String v) {
        return valueOf(v);
    }

}
