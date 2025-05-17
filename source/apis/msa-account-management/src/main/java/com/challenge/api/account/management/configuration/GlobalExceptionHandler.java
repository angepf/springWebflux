package com.challenge.api.account.management.configuration;

import com.challenge.api.account.management.domain.enums.ServiceErrors;
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
            errorMessage = ServiceErrors.NOT_FOUND.getErrorMessage();
        }
        ErrorResponse errorResponse = new ErrorResponse(
                ServiceErrors.NOT_FOUND.getErrorCode(), errorMessage
        );
        return ResponseEntity.status(ServiceErrors.NOT_FOUND.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = new ErrorResponse(ServiceErrors.CUSTOMER_SERVICE_ERROR.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ServiceErrors.CUSTOMER_SERVICE_ERROR.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException ex, ServerWebExchange exchange) {
        String errorMessage = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse(ServiceErrors.BAD_REQUEST.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ServiceErrors.BAD_REQUEST.getErrorCode(), errorMessage);

        return ResponseEntity.status(ServiceErrors.BAD_REQUEST.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleServerWebInputException(ServerWebInputException ex, ServerWebExchange exchange) {
        String errorMessage = ServiceErrors.INVALID_INPUT.getErrorMessage();
        if (ex.getCause() instanceof TypeMismatchException castEx) {
            errorMessage = ServiceErrors.INVALID_INPUT.getErrorMessage() + castEx.getPropertyName() + ". Provided value: " + castEx.getValue();
        } else if (ex.getCause() != null) {
            String causeMessage = ex.getCause().getMessage();
            if (causeMessage.contains("Cannot construct instance of")) {
                String enumTypeFull = causeMessage.split("`")[1];
                String enumType = enumTypeFull.substring(enumTypeFull.lastIndexOf('.') + 1);
                String unexpectedValue = causeMessage.split("'")[1];
                errorMessage = ServiceErrors.INVALID_INPUT.getErrorMessage() + enumType + ": " + unexpectedValue;
            } else {
                errorMessage = ServiceErrors.INVALID_INPUT.getErrorMessage() + ex.getCause().getMessage();
            }
        }
        ErrorResponse errorResponse = new ErrorResponse(ServiceErrors.INVALID_INPUT.getErrorCode(), errorMessage);
        return ResponseEntity.status(ServiceErrors.INVALID_INPUT.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = new ErrorResponse(ServiceErrors.INTERNAL_SERVER_ERROR.getErrorMessage(), ServiceErrors.INTERNAL_SERVER_ERROR.getErrorMessage() + ex.getMessage());
        return ResponseEntity.status(ServiceErrors.INVALID_INPUT.getStatusCode()).body(errorResponse);
    }

}