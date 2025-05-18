package com.challenge.api.customer.management.configuration;

import com.challenge.api.customer.management.domain.enums.CustomerError;
import com.challenge.api.customer.management.exception.CustomerException;
import com.challenge.api.customer.management.exception.ReportException;
import com.challenge.api.customer.management.service.models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleCustomerException(CustomerException ex) {
        log.error("CustomerException handled: {}", ex.getMessage(), ex);
        var error = ex.getError();
        return buildResponse(error.getCode(), ex.getMessage(), error.getHttpStatus());
    }

    @ExceptionHandler(ReportException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleReportException(ReportException ex) {
        log.error("ReportException handled: {}", ex.getMessage(), ex);
        var error = ex.getError();
        return buildResponse(error.getCode(), ex.getMessage(), error.getHttpStatus());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidation(WebExchangeBindException ex) {
        log.warn("Validation error: {}", ex.getMessage(), ex);
        var fieldError = ex.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse(CustomerError.BAD_REQUEST.getMessage());

        return buildResponse(CustomerError.BAD_REQUEST.getCode(), fieldError, CustomerError.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInput(ServerWebInputException ex) {
        log.warn("Input error: {}", ex.getMessage(), ex);
        String errorMessage = extractMessage(ex);
        return buildResponse(CustomerError.BAD_REQUEST.getCode(), errorMessage, CustomerError.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildResponse(
                CustomerError.INTERNAL_ERROR.getCode(),
                CustomerError.INTERNAL_ERROR.getMessage(),
                CustomerError.INTERNAL_ERROR.getHttpStatus()
        );
    }

    private Mono<ResponseEntity<ErrorResponse>> buildResponse(String code, String message, org.springframework.http.HttpStatus status) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(code);
        response.setMessage(message);
        return Mono.just(ResponseEntity.status(status).body(response));
    }

    private String extractMessage(ServerWebInputException ex) {
        if (ex.getCause() instanceof TypeMismatchException tm) {
            return "Invalid value for: " + tm.getPropertyName() + ". Value: " + tm.getValue();
        }
        return ex.getCause() != null ? ex.getCause().getMessage() : CustomerError.BAD_REQUEST.getMessage();
    }

}
