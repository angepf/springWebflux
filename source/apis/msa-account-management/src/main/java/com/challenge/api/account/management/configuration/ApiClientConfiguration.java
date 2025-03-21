package com.challenge.api.account.management.configuration;

import com.challenge.api.account.management.msa.customer.management.client.controller.CustomerManagementApi;
import com.challenge.api.account.management.util.JacocoAnnotationGenerated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@JacocoAnnotationGenerated
public class ApiClientConfiguration {

    @Bean
    public CustomerManagementApi customerManagementApi(
            @Value("${api.http-client.api.msa.customer-management.base-path}") String url) {
        var api = new CustomerManagementApi();
        api.getApiClient().setBasePath(url);
        return api;
    }

}
