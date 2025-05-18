package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Report;
import com.challenge.api.customer.management.domain.enums.ReportError;
import com.challenge.api.customer.management.exception.ReportException;
import com.challenge.api.customer.management.repository.PersonRepository;
import com.challenge.api.customer.management.repository.ReportRepository;
import com.challenge.api.customer.management.service.ReportService;
import com.challenge.api.customer.management.service.mapper.ReportMapper;
import com.challenge.api.customer.management.service.models.AccountReportResponse;
import com.challenge.api.customer.management.service.models.PostReportResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImpl implements ReportService {

    ReportRepository reportRepository;
    ReportMapper reportMapper;
    PersonRepository personRepository;

    @Override
    public Mono<AccountReportResponse> getReportByCustomerIdAndDate(String customerId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching report for customerId: {}, from: {} to: {}", customerId, startDate, endDate);

        return reportRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate)
                .flatMap(report ->
                        personRepository.findById(customerId)
                                .map(person -> reportMapper.toReportResponse(report, person.getName()))
                )
                .collectList()
                .map(reportList -> {
                    AccountReportResponse response = new AccountReportResponse();
                    response.setTransactions(reportList);
                    return response;
                })
                .doOnSuccess(resp -> log.info("Report generated successfully for customerId: {}", customerId))
                .onErrorMap(error -> {
                    log.error("Error generating report for customerId {}: {}", customerId, error.getMessage(), error);
                    return new ReportException(ReportError.GENERATION_FAILED, "Failed to retrieve report: " + error.getMessage());
                });
    }

    @Override
    public Mono<PostReportResponse> createReport(PostReportResponse reportResponse) {
        log.info("Creating report for customer: {}", reportResponse.getCustomerName());

        Report report = reportMapper.toReport(reportResponse);

        return reportRepository.save(report)
                .map(savedReport -> reportMapper.toReportResponse(savedReport, reportResponse.getCustomerName()))
                .doOnSuccess(resp -> log.info("Report saved successfully for customer: {}", reportResponse.getCustomerName()))
                .onErrorMap(error -> {
                    log.error("Error saving report for customer {}: {}", reportResponse.getCustomerName(), error.getMessage(), error);
                    return new ReportException(ReportError.INTERNAL_ERROR, "Failed to save report: " + error.getMessage());
                });
    }
}
