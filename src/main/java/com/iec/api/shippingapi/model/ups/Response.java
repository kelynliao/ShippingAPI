package com.iec.api.shippingapi.model.ups;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private TransactionReference TransactionReference;
    private ResponseStatus ResponseStatus;
    private List<Alert> Alert;

}
