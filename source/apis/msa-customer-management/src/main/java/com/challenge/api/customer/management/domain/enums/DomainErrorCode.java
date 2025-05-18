package com.challenge.api.customer.management.domain.enums;

import org.springframework.http.HttpStatus;

public interface DomainErrorCode {

    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();

}
