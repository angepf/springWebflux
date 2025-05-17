package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.domain.Account;
import com.challenge.api.account.management.domain.enums.ServiceErrors;
import com.challenge.api.account.management.exception.AccountException;
import com.challenge.api.account.management.repository.AccountRepository;
import com.challenge.api.account.management.service.AccountService;
import com.challenge.api.account.management.service.CustomerService;
import com.challenge.api.account.management.service.MovementService;
import com.challenge.api.account.management.service.mapper.AccountMapper;
import com.challenge.api.account.management.service.models.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;
    CustomerService customerService;
    MovementService movementService;

    @Override
    public Mono<PostAccountResponse> getAccountById(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountException(ServiceErrors.NOT_FOUND, accountNumber)))
                .map(accountMapper::toPostAccountResponse)
                .doOnSuccess(acc -> log.info("Account retrieved successfully: {}", acc))
                .doOnError(error -> log.error("Error retrieving account: {}", error.getMessage()))
                .onErrorMap(error -> new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage()));
    }

    @Override
    public Flux<PostAccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .map(accountMapper::toPostAccountResponse)
                .doOnComplete(() -> log.info("All accounts retrieved successfully"))
                .doOnError(error -> log.error("Error retrieving all accounts: {}", error.getMessage()))
                .onErrorMap(error -> new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage()));
    }

    private Mono<Account> validateAndSaveAccount(Account account, String customerId) {
        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new AccountException(ServiceErrors.NOT_FOUND, customerId)))
                .flatMap(customer -> accountRepository.save(account));
    }

    @Override
    public Mono<PostAccountResponse> createAccount(PostAccountRequest accountRequest) {
        Account account = accountMapper.toAccount(accountRequest);

        return validateAndSaveAccount(account, accountRequest.getCustomerId())
                .flatMap(savedAccount -> createFirstMovement(savedAccount.getAccountNumber(), accountRequest.getInitialBalance())
                        .thenReturn(accountMapper.toPostAccountResponse(savedAccount))
                        .onErrorResume(error -> accountRepository.delete(savedAccount)
                                .then(Mono.error(new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage())))))
                .doOnSuccess(accountResponse -> log.info("|--> Account created successfully: {}", accountResponse))
                .doOnError(error -> log.error("Error creating account: {}", error.getMessage()))
                .onErrorMap(error -> new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage()));
    }

    private Mono<PostMovementResponse> createFirstMovement(Long accountNumber, BigDecimal value) {
        PostMovementRequest postMovementRequest = new PostMovementRequest();
        postMovementRequest.setAccountNumber(accountNumber);
        postMovementRequest.setDate(LocalDate.now());
        postMovementRequest.setValue(value);
        postMovementRequest.setType(MovementTypeEnum.CREDIT);
        postMovementRequest.setStatus(true);

        return movementService.createMovement(postMovementRequest);
    }

    @Override
    public Mono<PostAccountResponse> updateAccount(Long accountNumber, PostAccountRequest accountRequest) {
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountException(ServiceErrors.NOT_FOUND, accountNumber)))
                .flatMap(existingAccount -> {
                    Account updatedAccount = accountMapper.toAccount(accountRequest);
                    updatedAccount.setAccountNumber(existingAccount.getAccountNumber());
                    return accountRepository.save(updatedAccount);
                })
                .map(accountMapper::toPostAccountResponse)
                .doOnSuccess(acc -> log.info("Account updated successfully: {}", acc))
                .doOnError(error -> log.error("Error updating account: {}", error.getMessage()))
                .onErrorMap(error -> new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage()));
    }

    @Override
    public Mono<DeleteResponse> deleteAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountException(ServiceErrors.NOT_FOUND, accountNumber)))
                .flatMap(existingAccount -> accountRepository.delete(existingAccount)
                        .then(Mono.just(createDeleteResponse(existingAccount.getAccountNumber()))))
                .doOnSuccess(response -> log.info("Account deleted successfully: {}", response))
                .doOnError(error -> log.error("Error deleting account: {}", error.getMessage()))
                .onErrorMap(error -> new AccountException(ServiceErrors.CUSTOMER_SERVICE_ERROR, error.getMessage()));
    }

    private DeleteResponse createDeleteResponse(Long accountNumber) {
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Account successfully deleted.");
        response.setId(accountNumber);
        return response;
    }

}