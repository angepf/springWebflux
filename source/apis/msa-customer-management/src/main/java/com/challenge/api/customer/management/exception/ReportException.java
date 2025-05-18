package com.challenge.api.customer.management.exception;

import com.challenge.api.customer.management.domain.enums.ReportError;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportException extends RuntimeException {

    ReportError error;

    public ReportException(ReportError error, String message) {
        super(message);
        this.error = error;
    }

}
