package com.challenge.api.customer.management.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MappingErrors {

    BAD_REQUEST("DDHIST-001", HttpStatus.BAD_REQUEST,
            "Bad request"),

    NOT_FOUND("DDHIST-002", HttpStatus.NOT_FOUND,
            "Resource not found"),

    METHOD_NOT_ALLOWED("DDHIST-003", HttpStatus.METHOD_NOT_ALLOWED,
            "The request method is not allowed"),

    TOO_MANY_REQUESTS("DDHIST-004", HttpStatus.TOO_MANY_REQUESTS,
            "Too many requests to the service, please try again later"),

    INTERNAL_SERVER_ERROR("DDHIST-005", HttpStatus.INTERNAL_SERVER_ERROR,
            "An error has ocurred, please contact your administrator"),

    BAD_GATEWAY("DDHIST-006", HttpStatus.BAD_GATEWAY,
            "An error has ocurred while calling an external service, please contact your administrator"),

    GATEWAY_TIMEOUT("DDHIST-007", HttpStatus.GATEWAY_TIMEOUT,
            "Could not connect to an external service, please contact your administrator");

    String errorCode;
    HttpStatus httpStatus;
    String message;

}