package com.iec.api.shippingapi.service;
import com.iec.api.shippingapi.model.canadaPost.PriceQuote;

import com.iec.api.shippingapi.model.canadaPost.ParcelCharacteristics;
import com.iec.api.shippingapi.model.canadaPost.Domestic;
import com.iec.api.shippingapi.model.canadaPost.Dimensions;
import com.iec.api.shippingapi.model.canadaPost.Destination;
import com.iec.api.shippingapi.dto.PackageInfo;
import com.iec.api.shippingapi.dto.ShippingAddress;
import com.iec.api.shippingapi.vo.ShippingRate;
import com.iec.api.shippingapi.dto.ShippingRateRequest;
import com.iec.api.shippingapi.model.canadaPost.MailingScenario;
import com.iec.api.shippingapi.model.canadaPost.PriceQuotes;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.XmlWriteFeature;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import com.iec.api.shippingapi.config.CanadaPostAccount;

@Service
public class CanadaPostService {
        private final CanadaPostAccount config;
        private final XmlMapper xmlMapper = XmlMapper.builder()
                        .enable(XmlWriteFeature.WRITE_XML_DECLARATION)
                        .build();

        public CanadaPostService(CanadaPostAccount config) {
                this.config = config;
        }


        public List<ShippingRate> getRates(ShippingRateRequest request)
                        throws IOException, InterruptedException {
                var mailingScenario = buildMailingScenario(request);

                String xml = xmlMapper.writeValueAsString(mailingScenario);
                String credentials = config.username() + ":" + config.password();

                String basicAuth = Base64.getEncoder().encodeToString(
                                credentials.getBytes(StandardCharsets.US_ASCII));

                String url = config.endpoint()+"/rs/ship/price";

                HttpRequest requestToCanadaPost = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Authorization", "Basic " + basicAuth)
                                .header("Accept", "application/vnd.cpc.ship.rate-v4+xml")
                                .header("Content-Type", "application/vnd.cpc.ship.rate-v4+xml")
                                .header("Accept-Language", "en-CA")
                                .POST(HttpRequest.BodyPublishers.ofString(
                                                xml,
                                                StandardCharsets.UTF_8))
                                .build();

                HttpResponse<String> response;
                try (HttpClient httpClient = HttpClient.newHttpClient()) {
                        response = httpClient.send(
                                        requestToCanadaPost,
                                        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                }
                PriceQuotes priceQuotes = parsePriceQuotes(response.body());
                return toShippingRates(priceQuotes);
        }

        private PriceQuotes parsePriceQuotes(String xml) {
                return xmlMapper.readValue(xml, PriceQuotes.class);
        }

        private List<ShippingRate> toShippingRates(PriceQuotes priceQuotes) {
                List<ShippingRate> shippingRates = new ArrayList<>();

                int id = 1;
                for (PriceQuote priceQuote : priceQuotes.getPriceQuotes()) {
                        String deliveryDay = null;
                        if (priceQuote.getServiceStandard() != null
                                        && priceQuote.getServiceStandard().getExpectedDeliveryDate() != null) {
                                deliveryDay = priceQuote.getServiceStandard().getExpectedDeliveryDate()
                                                .toXMLFormat();
                        }

                        shippingRates.add(new ShippingRate(
                                        id++,
                                        config.carrier(),
                                        priceQuote.getServiceName(),
                                        priceQuote.getServiceCode(),
                                        priceQuote.getPriceDetails().getDue(),
                                        deliveryDay));
                }

                return shippingRates;
        }

        public MailingScenario buildMailingScenario(ShippingRateRequest request) {
                ShippingAddress from = request.shippingFromAddress();
                ShippingAddress to = request.shippingToAddress();
                PackageInfo parcel = request.packageInfo();

                MailingScenario scenario = new MailingScenario();
                scenario.setCustomerNumber(config.customerNumber());

                scenario.setOriginPostalCode(cleanPostalCode(from.postalCode()));

                var characteristics = createCharacteristics(parcel);
                scenario.setParcelCharacteristics(characteristics);

                Domestic domestic = new Domestic();

                domestic.setPostalCode(cleanPostalCode(to.postalCode()));

                Destination destination = new Destination();

                destination.setDomestic(domestic);
                scenario.setDestination(destination);

                return scenario;
        }

        private static @NonNull ParcelCharacteristics createCharacteristics(PackageInfo parcel) {
                var characteristics = new ParcelCharacteristics();

                characteristics.setWeight(parcel.shippingWeight());

                Dimensions dimensions = new Dimensions();

                dimensions.setLength(BigDecimal.valueOf(parcel.dimensionLength()));
                dimensions.setWidth(BigDecimal.valueOf(parcel.dimensionWidth()));
                dimensions.setHeight(BigDecimal.valueOf(parcel.dimensionHeight()));

                characteristics.setDimensions(dimensions);
                return characteristics;
        }

        private String cleanPostalCode(String postalCode) {
                return postalCode.replaceAll("\\s+", "").toUpperCase();
        }
}
