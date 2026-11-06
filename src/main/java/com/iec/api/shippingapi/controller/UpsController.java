package com.iec.api.shippingapi.controller;


import com.iec.api.shippingapi.dto.ShippingRateRequest;
import com.iec.api.shippingapi.service.UpsService;
import com.iec.api.shippingapi.vo.ShippingRate;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ups")
public class UpsController {

    private final UpsService upsService;

    public UpsController(UpsService upsService){
        this.upsService = upsService;
    }

    @PostMapping("/getrates")
    public List<ShippingRate> getRates(
            @Valid @RequestBody ShippingRateRequest request) throws IOException, InterruptedException {
        return upsService.getRates(request);
    }
}
