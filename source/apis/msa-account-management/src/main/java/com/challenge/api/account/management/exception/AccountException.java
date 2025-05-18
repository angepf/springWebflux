package com.challenge.api.account.management.exception;

import com.challenge.api.account.management.domain.enums.AccountError;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountException extends RuntimeException {

    final AccountError accountError;
    final String errorCode;

    public AccountException(AccountError accountError, String message) {
        super(message);
        this.accountError = accountError;
        this.errorCode = "";
    }

    public AccountException(AccountError accountError, Long accountNumber) {
        super(accountError.getMessage());
        this.accountError = accountError;
        this.errorCode = accountError.getCode();
    }

    public AccountException(String errorCode, AccountError accountError, String message, Long accountNumber) {
        super(message + ", with id: [" + accountNumber + "]");
        this.accountError = accountError;
        this.errorCode = errorCode;
    }

}
