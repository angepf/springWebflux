package com.challenge.api.account.management.service;

import com.challenge.api.account.management.service.models.PostReportResponse;

public interface SqsService {

    void sendReport(PostReportResponse postReport);

}
