package com.challenge.api.customer.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PersonError {

    NOT_FOUND("MCMPE_001", "Person not found", HttpStatus.NOT_FOUND),
    INVALID_DATA("MCMPE_002", "Invalid person data", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("MCMPE_999", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
