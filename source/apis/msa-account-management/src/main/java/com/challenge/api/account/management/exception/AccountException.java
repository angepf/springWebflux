package com.challenge.api.account.management.exception;

public class AccountException extends RuntimeException {

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Long accountNumber) {
        super(message + ", with id: [" + accountNumber + "]");
    }
}
