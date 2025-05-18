package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.configuration.ClientRetryProperties;
import com.challenge.api.account.management.domain.enums.CustomerError;
import com.challenge.api.account.management.exception.CustomerException;
import com.challenge.api.account.management.msa.customer.management.client.controller.CustomerManagementApi;
import com.challenge.api.account.management.msa.customer.management.client.service.models.PostCustomerResponse;
import com.challenge.api.account.management.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    ClientRetryProperties clientRetryProperties;
    CustomerManagementApi customerManagementApi;

    @Override
    public Mono<PostCustomerResponse> getCustomerById(String identification) {
        return customerManagementApi.getCustomerById(identification)
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, identification)))
                .doOnSuccess(customer -> log.info("Customer retrieved successfully: {}", customer))
                .doOnError(error -> log.error("Error retrieving customer: {}", error.getMessage()))
                .onErrorMap(error -> {
                    if (error instanceof CustomerException) {
                        return error;
                    }
                    return new CustomerException(CustomerError.SERVICE_ERROR, "Failed to retrieve customer: " + error.getMessage());
                });
    }
}
