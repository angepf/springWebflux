package com.challenge.api.customer.management.controller;

import com.challenge.api.customer.management.service.ReportService;
import com.challenge.api.customer.management.service.models.AccountReportResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController implements ReportsApi {

    ReportService reportService;

    @Override
    public Mono<ResponseEntity<AccountReportResponse>> generateAccountReport(String customerId, LocalDate startDate, LocalDate endDate, ServerWebExchange exchange) {
        return reportService.getReportByCustomerIdAndDate(customerId, startDate, endDate)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
