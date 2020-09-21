package com.devonfw.application.mtsj.mtsjgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@EnableZuulProxy
@SpringBootApplication
public class MtsjGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtsjGatewayApplication.class, args);
	}

}
