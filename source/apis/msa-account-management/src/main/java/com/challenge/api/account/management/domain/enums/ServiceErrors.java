package com.challenge.api.account.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ServiceErrors {

    BAD_REQUEST("SEAM-001", HttpStatus.BAD_REQUEST, "Invalid request"),

    NOT_FOUND("SEAM-002", HttpStatus.NOT_FOUND, "Resource not found"),

    CUSTOMER_SERVICE_ERROR("SEAM-003", HttpStatus.BAD_REQUEST, "Internal error"),

    INVALID_INPUT("SEAM-004", HttpStatus.BAD_REQUEST, "Invalid input"),

    INTERNAL_SERVER_ERROR("SEAM-005", HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final String errorCode;
    private final HttpStatus statusCode;
    private final String errorMessage;

    public static ServiceErrors findHttpStatus(HttpStatus statusCode){
        return Arrays.stream(values())
                .filter(serviceErrors -> serviceErrors.statusCode.equals(statusCode))
                .findFirst().orElse(INTERNAL_SERVER_ERROR);
    }

}
