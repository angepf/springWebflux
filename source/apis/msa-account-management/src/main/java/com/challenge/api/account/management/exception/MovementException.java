package com.challenge.api.account.management.exception;

import com.challenge.api.account.management.domain.enums.MovementError;
import lombok.Getter;

@Getter
public class MovementException extends RuntimeException {

    private final MovementError error;

    public MovementException(MovementError error) {
        super(error.getMessage());
        this.error = error;
    }

    public MovementException(MovementError error, String details) {
        super(error.getMessage() + ": " + details);
        this.error = error;
    }
}
