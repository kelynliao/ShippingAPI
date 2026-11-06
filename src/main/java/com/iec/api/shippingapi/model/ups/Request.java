package com.iec.api.shippingapi.model.ups;

import lombok.Data;

@Data
//@JSONType(naming = PropertyNamingStrategy.PascalCase)
public class Request {
    private TransactionReference TransactionReference;
}
