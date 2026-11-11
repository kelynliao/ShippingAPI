package com.iec.api.shippingapi.service;

import com.iec.api.shippingapi.config.UpsAccount;
import com.iec.api.shippingapi.dto.ShippingRateRequest;
import com.iec.api.shippingapi.model.ups.*;
import com.iec.api.shippingapi.vo.ShippingRate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

import com.iec.api.shippingapi.model.ups.Package;

import java.math.BigDecimal;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("upsService")
public class UpsService {
    private final UpsAccount config;
    private final JsonMapper jsonMapper = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private final Map<String, String> serviceCodeList = new HashMap<>();
    public UpsService(UpsAccount config) {
        this.config = config;
        serviceCodeList.put("11","Standard");
        serviceCodeList.put("13","Express Saver (Next Day Air Saver)");
        serviceCodeList.put("01","Express (Next Day Air)");
        serviceCodeList.put("02","Expedited (2nd Day Air)");
        serviceCodeList.put("14","Express Early (Next Day Air Early AM)");

    }

    public List<ShippingRate> getRates(ShippingRateRequest shippingRateRequest)
            throws IOException, InterruptedException {
        List<ShippingRate>  shippingRateList = new ArrayList<>();
        var accessToken = getToken(config.clientId(), config.clientSecret());
        var rateRequest = new RateRequest();
        var request = new Request();
        var transactionReference = new TransactionReference();
        transactionReference.setTransactionIdentifier("222222222");
        transactionReference.setCustomerContext("111111111");
        request.setTransactionReference(transactionReference);
        rateRequest.setRequest(request);
        RateRequestRoot RateRequestRoot = new RateRequestRoot();
        RateRequestRoot.setRateRequest(rateRequest);

        // Shipment
        Shipment shipment = new Shipment();

        // shipTo
        var shipTo = new Ship();
        shipTo.setName(shippingRateRequest.shippingToAddress().firstName()+ " "+shippingRateRequest.shippingToAddress().lastName());
        Address shipToAddress =new Address();
        List<String> addressLines = new ArrayList<>();
        addressLines.add(shippingRateRequest.shippingToAddress().address1());
        if(StringUtils.hasText(shippingRateRequest.shippingToAddress().address2()))
            addressLines.add(shippingRateRequest.shippingToAddress().address2());
        shipToAddress.setAddressLine(addressLines);
        shipToAddress.setCity(shippingRateRequest.shippingToAddress().city());
        shipToAddress.setStateProvinceCode(shippingRateRequest.shippingToAddress().province());
        shipToAddress.setPostalCode(shippingRateRequest.shippingToAddress().postalCode());
        shipToAddress.setCountryCode(shippingRateRequest.shippingToAddress().countryCode());
        shipTo.setAddress(shipToAddress);

        // shipFrom
        var shipFrom = new Ship();
        shipFrom.setName(shippingRateRequest.shippingFromAddress().firstName()+ " "+shippingRateRequest.shippingFromAddress().lastName());
        Address shipFromAddress =new Address();
        List<String> addressLines2 = new ArrayList<>();
        addressLines2.add(shippingRateRequest.shippingFromAddress().address1());
        if(StringUtils.hasText(shippingRateRequest.shippingToAddress().address2()))
            addressLines.add(shippingRateRequest.shippingFromAddress().address2());
        shipFromAddress.setAddressLine(addressLines2);
        shipFromAddress.setCity(shippingRateRequest.shippingFromAddress().city());
        shipFromAddress.setStateProvinceCode(shippingRateRequest.shippingFromAddress().province());
        shipFromAddress.setPostalCode(shippingRateRequest.shippingFromAddress().postalCode());
        shipFromAddress.setCountryCode(shippingRateRequest.shippingFromAddress().countryCode());
        shipFrom.setAddress(shipFromAddress);

        // shipper
        Shipper shipper = new Shipper();
        shipper.setShipperNumber(config.accountNumber());
        shipper.setName(config.shipperName());
        shipper.setAddress(shipFromAddress);

        shipment.setShipper(shipper);
        shipment.setShipTo(shipTo);
        shipment.setShipFrom(shipFrom);

        //paymentInformation
        var paymentInformation = new PaymentInformation();
        var shipmentCharge = new ShipmentCharge();
        var billShipper = new BillShipper();
        billShipper.setAccountNumber(config.accountNumber());
        shipmentCharge.setType("01");
        shipmentCharge.setBillShipper(billShipper);
        paymentInformation.setShipmentCharge(shipmentCharge);
        shipment.setPaymentInformation(paymentInformation);


        var packaging = new Package();
        var packagingType = new PackagingType();
        packagingType.setCode("02");
        packagingType.setDescription("Packaging");
        packaging.setPackagingType(packagingType);
        var packageWeight = new PackageWeight();
        packageWeight.setWeight(shippingRateRequest.packageInfo().shippingWeight().toPlainString());
        var unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setCode(shippingRateRequest.packageInfo().weightUnits().toUpperCase(java.util.Locale.ROOT));
        packageWeight.setUnitOfMeasurement(unitOfMeasurement);
        packaging.setPackageWeight(packageWeight);

        var dimensions = new Dimensions();

        var unitOfMeasurement2 = new UnitOfMeasurement();
        unitOfMeasurement2.setCode(shippingRateRequest.packageInfo().dimensionUnits().toUpperCase(java.util.Locale.ROOT));
        dimensions.setUnitOfMeasurement(unitOfMeasurement2);
        dimensions.setWidth(Integer.toString(shippingRateRequest.packageInfo().dimensionWidth()));
        dimensions.setLength(Integer.toString(shippingRateRequest.packageInfo().dimensionLength()));
        dimensions.setHeight(Integer.toString(shippingRateRequest.packageInfo().dimensionHeight()));
        packaging.setDimensions(dimensions);
        shipment.setPackage(packaging);
        rateRequest.setShipment(shipment);
        //System.out.println(accessToken.getAccessToken());
        var requestRate = HttpRequest.newBuilder()
                .uri(URI.create(config.endPoint()+"/api/rating/v2403/shop"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + accessToken.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(
                        jsonMapper.writeValueAsString(RateRequestRoot), StandardCharsets.UTF_8))
                .build();
        System.out.println(jsonMapper.writeValueAsString(RateRequestRoot));
        String result = send(requestRate);

        var rateResponseRoot = jsonMapper.readValue(result, RateResponseRoot.class);
        var rateShipmentList = rateResponseRoot.getRateResponse().getRatedShipment();
        for(RatedShipment ratedShipment : rateShipmentList) {
            String code = ratedShipment.getService().getCode();
            shippingRateList.add(new ShippingRate(shippingRateList.size() + 1, "UPS",
                    serviceCodeList.getOrDefault(code, code), code,
                    new BigDecimal(ratedShipment.getTotalCharges().getMonetaryValue()), ""));
        }
        return shippingRateList;
    }

    private AccessToken getToken(String clientId, String clientSecret)
            throws IOException, InterruptedException {

        // todo: add a cache to reuse access token.
        var url = config.endPoint()+"/security/v1/oauth/token";
        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) { throw new IllegalStateException("UPS credentials must be configured"); }
        String basicAuth = Base64.getEncoder().encodeToString(
                (clientId + ":" + clientSecret).getBytes(StandardCharsets.ISO_8859_1));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + basicAuth)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
        var accessToken = jsonMapper.readValue(send(request), AccessToken.class);
        if (accessToken == null || accessToken.getAccessToken() == null
                || accessToken.getAccessToken().isBlank()) {
            throw new IllegalStateException("UPS returned an empty access token");
        }
        return accessToken;
    }

    private String send(HttpRequest request) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            var response = client.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
           if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("UPS request failed with HTTP status " + response.statusCode());
            }
            return response.body();
        }
    }
}
