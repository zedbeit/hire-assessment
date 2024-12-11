package com.payu.hire.assessment.book_catalog_web_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class JaxRsClientConfig {

    @Bean
    public Client client() {
        return ClientBuilder
                .newClient();
    }
}
