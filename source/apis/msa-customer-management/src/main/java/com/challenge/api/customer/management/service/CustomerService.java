package com.challenge.api.customer.management.service;

import com.challenge.api.customer.management.service.models.DeleteCustomerResponse;
import com.challenge.api.customer.management.service.models.PostCustomerRequest;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<PostCustomerResponse> createCustomer(PostCustomerRequest customerRequest);

    Mono<PostCustomerResponse> getCustomerById(String identification);

    Flux<PostCustomerResponse> getAllCustomers();

    Mono<PostCustomerResponse> updateCustomer(String identification, PostCustomerRequest customerRequest);

    Mono<DeleteCustomerResponse> deleteCustomer(String identification);
}