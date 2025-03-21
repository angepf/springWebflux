package com.challenge.api.account.management.exception;

public class CustomerException extends RuntimeException {

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, String identification) {
        super(message + ", identification: [" + identification + "]");
    }

}