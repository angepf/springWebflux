package com.challenge.api.account.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MovementError implements DomainErrorCode {

    BAD_REQUEST("MAMME-001", "Bad request", HttpStatus.BAD_REQUEST),
    NOT_FOUND("MAMME-002", "Movement not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_FUNDS("MAMME-003", "Insufficient funds", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("MAMME-005", "Unexpected internal error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
