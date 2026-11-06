package com.iec.api.shippingapi.controller;

import com.iec.api.shippingapi.dto.ShippingRateRequest;
import com.iec.api.shippingapi.vo.ShippingRate;
import com.iec.api.shippingapi.service.CanadaPostService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/canadapost")
public class CanadaPostController {

    private final CanadaPostService canadaPostService;

    public CanadaPostController(CanadaPostService canadaPostService) {
        this.canadaPostService = canadaPostService;
    }

    @PostMapping("/getrates")
    public List<ShippingRate> getRates(
            @Valid @RequestBody ShippingRateRequest request) throws IOException, InterruptedException {
        return canadaPostService.getRates(request);
    }
}
