package com.challenge.api.customer.management.exception;

import com.challenge.api.customer.management.domain.enums.CustomerError;
import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {

    private final CustomerError error;

    public CustomerException(CustomerError error) {
        super(error.getMessage());
        this.error = error;
    }

    public CustomerException(CustomerError error, String details) {
        super(error.getMessage() + ": " + details);
        this.error = error;
    }
}