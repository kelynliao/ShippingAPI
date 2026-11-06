package com.iec.api.shippingapi;
import com.iec.api.shippingapi.model.canadaPost.Services;

import com.iec.api.shippingapi.config.CanadaPostAccount;
import com.iec.api.shippingapi.dto.PackageInfo;
import com.iec.api.shippingapi.dto.ShippingAddress;
import com.iec.api.shippingapi.dto.ShippingRateRequest;
import com.iec.api.shippingapi.model.canadaPost.MailingScenario;
import com.iec.api.shippingapi.model.canadaPost.PriceQuotes;
import com.iec.api.shippingapi.service.CanadaPostService;

import org.junit.jupiter.api.Test;
import tools.jackson.dataformat.xml.XmlMapper;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class CanadaPostXmlBindingTests {
    private static final String NAMESPACE = "http://www.canadapost.ca/ws/ship/rate-v4";
    private static final String RESPONSE = """
            <price-quotes xmlns="http://www.canadapost.ca/ws/ship/rate-v4">
              <price-quote>
                <service-code>DOM.EP</service-code>
                <service-link href="https://example.com/service" rel="service" media-type="application/xml"/>
                <service-name>Expedited Parcel</service-name>
                <price-details><base>10.00</base><taxes><gst percent="5">0.50</gst></taxes><due>10.50</due></price-details>
                <service-standard><expected-delivery-date>2026-11-03</expected-delivery-date></service-standard>
              </price-quote>
              <price-quote>
                <service-code>DOM.XP</service-code><service-name>Xpresspost</service-name>
                <price-details><base>20.00</base><due>21.00</due></price-details>
              </price-quote>
            </price-quotes>
            """;

    @Test
    void writesMailingScenarioUsingCanadaPostElementNamesAndNamespace() throws Exception {

        var config = new CanadaPostAccount("Canada Post", "test", "test", "1234567890", null);
        var from = new ShippingAddress(null, null, null, null, null, null, null, null, null, "k1a 0b1", null);
        var to = new ShippingAddress(null, null, null, null, null, null, null, null, null, "v6b 1a1", null);
        var parcel = new PackageInfo(new BigDecimal("1.250"), 0, 10, 20, 30, 1, "CAD", "KG", "LB");
        var request = new ShippingRateRequest(to, from, parcel);

        var scenario = new CanadaPostService(config).buildMailingScenario(request);
        String xml = new XmlMapper().writeValueAsString(scenario);

        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        var document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(
                xml.getBytes(StandardCharsets.UTF_8)));
        assertEquals("mailing-scenario", document.getDocumentElement().getLocalName());
        assertEquals(NAMESPACE, document.getDocumentElement().getNamespaceURI());
        assertEquals("K1A0B1", document.getElementsByTagNameNS(NAMESPACE, "origin-postal-code").item(0).getTextContent());
        assertEquals("V6B1A1", document.getElementsByTagNameNS(NAMESPACE, "postal-code").item(0).getTextContent());
        assertEquals("1.250", document.getElementsByTagNameNS(NAMESPACE, "weight").item(0).getTextContent());
        assertEquals(0, document.getElementsByTagNameNS("*", "contract-id").getLength());
        assertEquals(1, document.getElementsByTagNameNS(NAMESPACE, "parcel-characteristics").getLength());

    }

    @Test
    void bindsAttributesTextAndUnwrappedLists() {
        var mapper = new XmlMapper();
        var quotes = mapper.readValue(RESPONSE, PriceQuotes.class);
        var first = quotes.getPriceQuotes().getFirst();
        assertEquals("2026-11-03", first.getServiceStandard().getExpectedDeliveryDate().toXMLFormat());
        assertEquals("https://example.com/service", first.getServiceLink().getHref());
        assertEquals(new BigDecimal("5"), first.getPriceDetails().getTaxes().getGst().getPercent());
        assertEquals(new BigDecimal("0.50"), first.getPriceDetails().getTaxes().getGst().getValue());
        assertEquals(2, mapper.readValue(mapper.writeValueAsString(quotes), PriceQuotes.class).getPriceQuotes().size());
    }

    @Test
    void writesDateAndServiceCodesInSchemaFormat() throws Exception {
        var scenario = new MailingScenario();
        scenario.setExpectedMailingDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2026-11-02"));
        var services = new Services();
        services.getServiceCodes().add("DOM.EP");
        services.getServiceCodes().add("DOM.XP");
        scenario.setServices(services);
        String xml = new XmlMapper().writeValueAsString(scenario);
        assertTrue(xml.contains(">2026-11-02</"), xml);
        assertEquals(2, new XmlMapper().readValue(xml, MailingScenario.class).getServices().getServiceCodes().size());
        assertFalse(xml.contains("serviceCodes"), xml);
    }
}
