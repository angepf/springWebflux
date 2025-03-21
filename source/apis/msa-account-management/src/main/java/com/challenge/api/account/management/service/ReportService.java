package com.challenge.api.account.management.service;

import com.challenge.api.account.management.domain.Account;
import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.service.models.AccountReportResponse;
import com.challenge.api.account.management.service.models.PostReportResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {

    Mono<AccountReportResponse> generateReport(String customerId, LocalDate startDate, LocalDate endDate);

    Mono<Void> publishReportResponseNew(Movement movement);

}
