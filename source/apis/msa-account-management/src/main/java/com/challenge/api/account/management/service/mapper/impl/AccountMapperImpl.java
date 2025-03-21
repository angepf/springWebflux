package com.challenge.api.account.management.service.mapper.impl;

import com.challenge.api.account.management.domain.Account;
import com.challenge.api.account.management.service.mapper.AccountMapper;
import com.challenge.api.account.management.service.models.PostAccountRequest;
import com.challenge.api.account.management.service.models.PostAccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(PostAccountRequest postAccountRequest) {
        return Account.builder()
                .accountNumber(postAccountRequest.getAccountNumber())
                .customerId(postAccountRequest.getCustomerId())
                .type(postAccountRequest.getType())
                .initialBalance(postAccountRequest.getInitialBalance())
                .status(postAccountRequest.getStatus())
                .build();
    }

    @Override
    public PostAccountResponse toPostAccountResponse(Account account) {
        PostAccountResponse postAccountResponse = new PostAccountResponse();
        postAccountResponse.setAccountNumber(account.getAccountNumber());
        postAccountResponse.setCustomerId(account.getCustomerId());
        postAccountResponse.setType(account.getType());
        postAccountResponse.setInitialBalance(account.getInitialBalance());
        postAccountResponse.setStatus(account.getStatus());

        return postAccountResponse;
    }
}
