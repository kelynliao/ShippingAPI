package com.iec.api.shippingapi.model.ups;


import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class TransactionReference {
    private String TransactionIdentifier;
    private String CustomerContext;
}

