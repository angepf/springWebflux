package com.challenge.api.account.management.exception;

public class MovementException extends RuntimeException {

    public MovementException(String message) {
        super(message);
    }

    public MovementException(String message, Long accountNumber) {
        super(message + ", with id: [" + accountNumber + "]");
    }
}
