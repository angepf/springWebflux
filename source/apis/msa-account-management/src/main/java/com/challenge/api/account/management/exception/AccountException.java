package com.challenge.api.account.management.exception;

import com.challenge.api.account.management.domain.enums.ServiceErrors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountException extends RuntimeException {

    final ServiceErrors serviceErrors;
    final String errorCode;

    public AccountException(ServiceErrors serviceErrors, String message) {
        super(message);
        this.serviceErrors = serviceErrors;
        this.errorCode = "";
    }

    public AccountException(ServiceErrors serviceErrors, Long accountNumber) {
        super(serviceErrors.getErrorMessage());
        this.serviceErrors = serviceErrors;
        this.errorCode = serviceErrors.getErrorCode();

    }

    public AccountException(String errorCode, ServiceErrors serviceErrors, String message, Long accountNumber) {
        super(message + ", with id: [" + accountNumber + "]");
        this.serviceErrors = serviceErrors;
        this.errorCode = errorCode;
    }

}
