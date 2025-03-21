package com.challenge.api.customer.management.exception;

import com.challenge.api.customer.management.domain.enums.MappingErrors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportException extends RuntimeException {

    final MappingErrors mappingErrors;

    public ReportException(MappingErrors mappingErrors, String message) {
        super(message);
        this.mappingErrors = mappingErrors;
    }


}