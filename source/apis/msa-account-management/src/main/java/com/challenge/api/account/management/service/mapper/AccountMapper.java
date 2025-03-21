package com.challenge.api.account.management.service.mapper;

import com.challenge.api.account.management.domain.Account;
import com.challenge.api.account.management.service.models.PostAccountRequest;
import com.challenge.api.account.management.service.models.PostAccountResponse;

public interface AccountMapper {

    Account toAccount(PostAccountRequest postAccountRequest);

    PostAccountResponse toPostAccountResponse(Account account);

}