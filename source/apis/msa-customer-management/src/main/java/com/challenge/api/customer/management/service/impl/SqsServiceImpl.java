package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.service.ReportService;
import com.challenge.api.customer.management.service.SqsService;
import com.challenge.api.customer.management.service.models.PostReportResponse;
import com.challenge.api.customer.management.util.JsonUtils;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SqsServiceImpl implements SqsService {

    ReportService reportService;

    @SqsListener(value = "${aws.sqs.report-queue}")
    public void listenForNewReports(String sqsMessage) {
        log.atDebug().setMessage("Start method processMessagesFromSQS :: Parameters[{}]")
                .addArgument(sqsMessage).log();

        Mono.just(sqsMessage)
                .map(message -> JsonUtils.convertJsonToObject(message, PostReportResponse.class))
                .doOnNext(sqsRequestMessage -> log.info("Processing message: {}", sqsRequestMessage))
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(sqsRequestMessage -> {
                    reportService.createReport(sqsRequestMessage).subscribe();
                    log.info("End event processMessagesFromSQS");
                })
                .doOnError(exception -> log.atError().setMessage(exception.getLocalizedMessage())
                        .setCause(exception.getCause()).log())
                .subscribe();
    }

}