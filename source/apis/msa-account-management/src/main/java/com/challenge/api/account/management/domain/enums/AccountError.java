package com.challenge.api.account.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AccountError implements DomainErrorCode {

    BAD_REQUEST("MAMAE-001", "Bad request", HttpStatus.BAD_REQUEST),
    NOT_FOUND("MAMAE-002", "Account not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND("MAMAE-003", "Customer not found", HttpStatus.NOT_FOUND),
    CREATION_FAILED("MAMAE-004", "Failed to create account", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_FAILED("MAMAE-005", "Failed to update account", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FAILED("MAMAE-006", "Failed to delete account", HttpStatus.INTERNAL_SERVER_ERROR),
    MOVEMENT_CREATION_FAILED("MAMAE-007", "Failed to create initial movement", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR("MAMAE-008", "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE("MAMAE-009", "Error calling external service", HttpStatus.BAD_GATEWAY),
    TIMEOUT("MAMAE-010", "Timeout contacting external service", HttpStatus.GATEWAY_TIMEOUT),
    CUSTOMER_SERVICE_ERROR("MAMAE-011", "Customer service error", HttpStatus.BAD_REQUEST),
    ACCOUNT_SERVICE_ERROR("MAMAE-012", "Account service error", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
