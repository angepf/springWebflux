package com.challenge.api.account.management.controller;

import com.challenge.api.account.management.service.AccountService;
import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostAccountRequest;
import com.challenge.api.account.management.service.models.PostAccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AccountController implements AccountsApi {

    AccountService accountService;

    @Override
    public Mono<ResponseEntity<Flux<PostAccountResponse>>> getAllAccounts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(accountService.getAllAccounts()));
    }

    @Override
    public Mono<ResponseEntity<PostAccountResponse>> getAccountById(Long accountNumber, ServerWebExchange exchange) {
        return accountService.getAccountById(accountNumber)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<PostAccountResponse>> createAccount(@Valid @RequestBody PostAccountRequest postAccountRequest, ServerWebExchange exchange) {
        return accountService.createAccount(postAccountRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<PostAccountResponse>> updateAccountById(Long accountNumber, PostAccountRequest postAccountRequest, ServerWebExchange exchange) {
        return accountService.updateAccount(accountNumber, postAccountRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<DeleteResponse>> deleteAccountById(Long accountNumber, ServerWebExchange exchange) {
        return accountService.deleteAccount(accountNumber)
                .map(ResponseEntity::ok);
    }

}
