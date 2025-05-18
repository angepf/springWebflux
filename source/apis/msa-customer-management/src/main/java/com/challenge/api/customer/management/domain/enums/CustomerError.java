package com.challenge.api.customer.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerError implements DomainErrorCode {

    BAD_REQUEST("MCMCECE-001", "Bad request", HttpStatus.BAD_REQUEST),
    NOT_FOUND("MCMCE-002", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("MCMCE-005", "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE("MCMCE-006", "Error calling external service", HttpStatus.BAD_GATEWAY),
    TIMEOUT("MCMCE-007", "Timeout contacting external service", HttpStatus.GATEWAY_TIMEOUT),
    ACCOUNT_SERVICE_ERROR("MCMCE-008", "Account service error", HttpStatus.BAD_REQUEST),
    CUSTOMER_SERVICE_ERROR("MCMCE-009", "Customer service error", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
