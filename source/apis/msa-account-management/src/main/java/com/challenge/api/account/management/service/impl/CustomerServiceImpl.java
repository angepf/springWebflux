package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.configuration.ClientRetryProperties;
import com.challenge.api.account.management.exception.CustomerException;
import com.challenge.api.account.management.msa.customer.management.client.controller.CustomerManagementApi;
import com.challenge.api.account.management.msa.customer.management.client.service.models.PostCustomerResponse;
import com.challenge.api.account.management.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    ClientRetryProperties clientRetryProperties;
    CustomerManagementApi customerManagementApi;

    @Override
    public Mono<PostCustomerResponse> getCustomerById(String identification) {
        return customerManagementApi.getCustomerById(identification)
                .switchIfEmpty(Mono.error(new CustomerException("Customer not found", identification)))
                .doOnSuccess(acc -> log.info("Account retrieved successfully: {}", acc))
                .doOnError(error -> log.error("Error retrieving account: {}", error.getMessage()))
                .onErrorMap(error -> new CustomerException("Failed to retrieve account: " + error.getMessage()));

    }

}