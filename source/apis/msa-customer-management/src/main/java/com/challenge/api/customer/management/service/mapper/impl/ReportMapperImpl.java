package com.challenge.api.customer.management.service.mapper.impl;

import com.challenge.api.customer.management.domain.Report;
import com.challenge.api.customer.management.service.mapper.ReportMapper;
import com.challenge.api.customer.management.service.models.PostReportResponse;
import org.springframework.stereotype.Component;

@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report toReport(PostReportResponse postReportResponse) {
        return Report.builder()
                .date(postReportResponse.getDate())
                .customerId(postReportResponse.getCustomerName())
                .typeAccount(postReportResponse.getTypeAccount())
                .typeMovement(postReportResponse.getTypeMovement())
                .status(postReportResponse.getStatus())
                .value(postReportResponse.getValue())
                .accountNumber(postReportResponse.getAccountNumber())
                .balance(postReportResponse.getBalance())
                .initialBalance(postReportResponse.getInitialBalance())
                .build();
    }

    @Override
    public PostReportResponse toReportResponse(Report report, String customerName) {
        PostReportResponse reportResponse = new PostReportResponse();
        reportResponse.accountNumber(report.getAccountNumber());
        reportResponse.balance(report.getBalance());
        reportResponse.customerName(customerName);
        reportResponse.date(report.getDate());
        reportResponse.initialBalance(report.getInitialBalance());
        reportResponse.status(report.getStatus());
        reportResponse.typeAccount(report.getTypeAccount());
        reportResponse.typeMovement(report.getTypeMovement());
        reportResponse.value(report.getValue());

        return reportResponse;
    }
}
