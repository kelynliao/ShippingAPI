package com.iec.api.shippingapi;

import com.iec.api.shippingapi.controller.CanadaPostController;
import com.iec.api.shippingapi.exception.ValidationExceptionHandler;
import com.iec.api.shippingapi.service.CanadaPostService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasItem;

class ShippingRateRequestValidationTests {
    @Test
    void rejectsEachMissingRequestObjectBeforeCallingCarrier() throws Exception {
        var service = mock(CanadaPostService.class);
        try (var validator = new LocalValidatorFactoryBean()) {
            validator.afterPropertiesSet();
            var mvc = MockMvcBuilders.standaloneSetup(new CanadaPostController(service))
                    .setControllerAdvice(new ValidationExceptionHandler())
                    .setValidator(validator).build();

            for (String body : new String[] {
                    "{}",
                    "{\"shippingFromAddress\":{}}",
                    "{\"shippingToAddress\":{}}",
                    "{\"shippingToAddress\":{},\"shippingFromAddress\":{}}"
            }) {
                mvc.perform(post("/canadapost/getrates")
                                .contentType(MediaType.APPLICATION_JSON).content(body))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("Request validation failed"))
                        .andExpect(jsonPath("$.errors").isNotEmpty());
            }
            mvc.perform(post("/canadapost/getrates")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"shippingToAddress":{},"shippingFromAddress":{},
                                     "packageInfo":{"shippingWeight":0,"shippingInsurance":-1,
                                     "dimensionLength":1,"dimensionWidth":1,"dimensionHeight":1,
                                     "groupPackageCount":1,"currency":"CAD","weightUnits":"kg","dimensionUnits":"cm"}}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors[?(@.field == 'packageInfo.shippingWeight')].message",
                            hasItem("Shipping weight must be greater than zero")))
                    .andExpect(jsonPath("$.errors[?(@.field == 'packageInfo.shippingInsurance')].message",
                            hasItem("Shipping insurance must not be negative")));
            mvc.perform(post("/canadapost/getrates")
                            .contentType(MediaType.APPLICATION_JSON).content("{\"packageInfo\":{}}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(
                            "Invalid request body: provide valid JSON with all required fields and correct value types"));
            verifyNoInteractions(service);
        }
    }
}
