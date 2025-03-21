package com.challenge.api.customer.management.service.mapper;

import com.challenge.api.customer.management.domain.Report;
import com.challenge.api.customer.management.service.models.PostReportResponse;

public interface ReportMapper {

    Report toReport(PostReportResponse postReportResponse);

    PostReportResponse toReportResponse(Report report, String customerName);

}
