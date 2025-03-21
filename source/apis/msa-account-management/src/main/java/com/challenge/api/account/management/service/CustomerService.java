package com.challenge.api.account.management.service;

import com.challenge.api.account.management.msa.customer.management.client.service.models.PostCustomerResponse;
import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostAccountRequest;
import com.challenge.api.account.management.service.models.PostAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<PostCustomerResponse> getCustomerById(String identification);

}