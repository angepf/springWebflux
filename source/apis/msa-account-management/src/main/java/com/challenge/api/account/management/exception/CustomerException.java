package com.challenge.api.account.management.exception;

import com.challenge.api.account.management.domain.enums.CustomerError;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerException extends RuntimeException {

    final CustomerError error;
    final String details;

    public CustomerException(CustomerError error) {
        super(error.getMessage());
        this.error = error;
        this.details = null;
    }

    public CustomerException(CustomerError error, String details) {
        super(error.getMessage() + (details != null ? ", details: [" + details + "]" : ""));
        this.error = error;
        this.details = details;
    }

}
