package com.challenge.api.customer.management.exception;

import com.challenge.api.customer.management.domain.enums.PersonError;
import lombok.Getter;

@Getter
public class PersonException extends RuntimeException {

    private final PersonError error;

    public PersonException(PersonError error, String message) {
        super(message);
        this.error = error;
    }

}
