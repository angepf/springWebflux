package com.challenge.api.account.management.domain.enums;

import org.springframework.http.HttpStatus;

public interface DomainErrorCode {

    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();

}
