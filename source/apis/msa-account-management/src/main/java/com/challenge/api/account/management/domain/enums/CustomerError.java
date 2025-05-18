package com.challenge.api.account.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerError {

    NOT_FOUND("MAMCE-001", "Customer not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("MAMCE-005", "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_ERROR("MAMCE-006", "Customer service error", HttpStatus.BAD_GATEWAY);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
