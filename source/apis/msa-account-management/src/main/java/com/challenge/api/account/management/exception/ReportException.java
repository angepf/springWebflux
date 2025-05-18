package com.challenge.api.account.management.exception;

import com.challenge.api.account.management.domain.enums.ReportError;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportException extends RuntimeException {

    final ReportError error;
    final String details;

    public ReportException(ReportError error) {
        super(error.getMessage());
        this.error = error;
        this.details = null;
    }

    public ReportException(ReportError error, String details) {
        super(error.getMessage() + (details != null ? ", details: [" + details + "]" : ""));
        this.error = error;
        this.details = details;
    }
}
