package com.challenge.api.account.management.service;

import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostAccountRequest;
import com.challenge.api.account.management.service.models.PostAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<PostAccountResponse> createAccount(PostAccountRequest accountRequest);

    Mono<PostAccountResponse> getAccountById(Long accountNumber);

    Flux<PostAccountResponse> getAllAccounts();

    Mono<PostAccountResponse> updateAccount(Long accountNumber, PostAccountRequest accountRequest);

    Mono<DeleteResponse> deleteAccount(Long accountNumber);
}