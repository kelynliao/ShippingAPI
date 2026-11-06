package com.iec.api.shippingapi.model.canadaPost;

public enum CcType {

    MC,
    VIS,
    AME,
    DC,
    DIS,
    ER;

    public String value() {
        return name();
    }

    public static CcType fromValue(String v) {
        return valueOf(v);
    }

}
