package com.iec.api.shippingapi;

import com.iec.api.shippingapi.config.CanadaPostAccount;
import com.iec.api.shippingapi.config.UpsAccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({CanadaPostAccount.class, UpsAccount.class})
@SpringBootApplication
public class IecapiShippingapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IecapiShippingapiApplication.class, args);
	}

}
