package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Report;
import com.challenge.api.customer.management.domain.enums.MappingErrors;
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
        return reportRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate)
                .flatMap(report -> personRepository.findById(customerId)
                        .map(person -> reportMapper.toReportResponse(report, person.getName()))
                )
                .collectList()
                .map(reports -> {
                    AccountReportResponse accountReportResponse = new AccountReportResponse();
                    accountReportResponse.setTransactions(reports);
                    return accountReportResponse;
                })
                .doOnSuccess(reportResponse -> log.info("Report retrieved successfully"))
                .doOnError(error -> log.error("Error retrieving report: {}", error.getMessage()))
                .onErrorMap(error -> new ReportException(MappingErrors.BAD_GATEWAY, "Failed to retrieve account: " + error.getMessage()));
    }

    @Override
    public Mono<PostReportResponse> createReport(PostReportResponse reportResponse) {
        Report report = reportMapper.toReport(reportResponse);
        return reportRepository.save(report)
                .map(savedReport -> reportMapper.toReportResponse(savedReport, reportResponse.getCustomerName()))
                .doOnSuccess(savedReport -> log.info("Report saved successfully: {}", savedReport))
                .doOnError(error -> log.error("Error saving report: {}", error.getMessage()))
                .onErrorMap(error -> new ReportException(MappingErrors.BAD_GATEWAY, "Failed to save report: " + error.getMessage()));
    }

}