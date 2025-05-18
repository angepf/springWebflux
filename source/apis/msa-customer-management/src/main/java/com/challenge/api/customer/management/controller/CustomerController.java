package com.challenge.api.customer.management.controller;

import com.challenge.api.customer.management.service.CustomerService;
import com.challenge.api.customer.management.service.models.DeleteCustomerResponse;
import com.challenge.api.customer.management.service.models.PostCustomerRequest;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController implements CustomersApi {

    CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Flux<PostCustomerResponse>>> getAllCustomers(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(customerService.getAllCustomers()));
    }

    @Override
    public Mono<ResponseEntity<PostCustomerResponse>> getCustomerById(String identification, ServerWebExchange exchange) {
        return customerService.getCustomerById(identification)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<PostCustomerResponse>> postCustomer(@Valid PostCustomerRequest postCustomerRequest, ServerWebExchange exchange) {
        return customerService.createCustomer(postCustomerRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<PostCustomerResponse>> updateCustomerById(String customerId, @Valid PostCustomerRequest postCustomerRequest, ServerWebExchange exchange) {
        return customerService.updateCustomer(customerId, postCustomerRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<DeleteCustomerResponse>> deleteCustomerById(@PathVariable String customerId, ServerWebExchange exchange) {
        return customerService.deleteCustomer(customerId)
                .map(ResponseEntity::ok);
    }

}
