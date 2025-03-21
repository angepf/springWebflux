package com.challenge.api.account.management.configuration;

import com.challenge.api.account.management.exception.AccountException;
import com.challenge.api.account.management.exception.CustomerException;
import com.challenge.api.account.management.service.models.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorResponse> handleAccountException(AccountException ex, ServerWebExchange exchange) {
        String errorMessage = ex.getMessage();
        if (errorMessage.contains("404 Not Found")) {
            errorMessage = "Customer not found";
        }
        ErrorResponse errorResponse = new ErrorResponse("ACCOUNT_ERROR", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = new ErrorResponse("CUSTOMER_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException ex, ServerWebExchange exchange) {
        String errorMessage = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleServerWebInputException(ServerWebInputException ex, ServerWebExchange exchange) {
        String errorMessage = "Invalid input";
        if (ex.getCause() instanceof TypeMismatchException castEx) {
            errorMessage = "Invalid value for field: " + castEx.getPropertyName() + ". Provided value: " + castEx.getValue();
        } else if (ex.getCause() != null) {
            String causeMessage = ex.getCause().getMessage();
            if (causeMessage.contains("Cannot construct instance of")) {
                String enumTypeFull = causeMessage.split("`")[1];
                String enumType = enumTypeFull.substring(enumTypeFull.lastIndexOf('.') + 1);
                String unexpectedValue = causeMessage.split("'")[1];
                errorMessage = "Invalid value for " + enumType + ": " + unexpectedValue;
            } else {
                errorMessage = "Invalid input: " + ex.getCause().getMessage();
            }
        }
        ErrorResponse errorResponse = new ErrorResponse("ENUM_ERROR", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}