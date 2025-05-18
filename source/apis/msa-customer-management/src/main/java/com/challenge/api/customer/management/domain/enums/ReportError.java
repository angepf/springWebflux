package com.challenge.api.customer.management.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportError {

    MCMRE_NOT_FOUND("MCMRE_001", "Report not found", HttpStatus.NOT_FOUND),
    GENERATION_FAILED("MCMRE_002", "Failed to generate report", HttpStatus.BAD_REQUEST),
    INVALID_INPUT("MCMRE_003", "Invalid input provided for report generation", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_ERROR("MCMRE_999", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
