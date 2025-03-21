package com.challenge.api.customer.management.service;

import com.challenge.api.customer.management.service.models.AccountReportResponse;
import com.challenge.api.customer.management.service.models.PostReportResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {

    Mono<AccountReportResponse> getReportByCustomerIdAndDate(String customerId, LocalDate startDate, LocalDate endDate);

    Mono<PostReportResponse> createReport(PostReportResponse reportResponse);

}