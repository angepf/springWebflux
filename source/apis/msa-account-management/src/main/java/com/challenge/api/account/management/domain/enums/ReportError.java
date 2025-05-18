package com.challenge.api.account.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportError implements DomainErrorCode {

    CUSTOMER_NOT_FOUND("MAMRE-001", "Customer not found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND("MAMRE-002", "Account not found", HttpStatus.NOT_FOUND),
    MOVEMENTS_NOT_FOUND("MAMRE-003", "No movements found for the given criteria", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("MAMRE-500", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
