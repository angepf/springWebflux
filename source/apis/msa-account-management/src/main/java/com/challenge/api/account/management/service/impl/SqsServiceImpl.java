package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.service.SqsService;
import com.challenge.api.account.management.service.models.PostReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SqsServiceImpl implements SqsService {

    SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    public void sendReport(PostReportResponse postReport) {
        try {
            String messageBody = objectMapper.writeValueAsString(postReport);
            String queueUrl = "https://sqs.us-east-1.amazonaws.com/889198008697/report-sqs";
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();
            SendMessageResponse sendMsgResponse = sqsClient.sendMessage(sendMsgRequest);
            System.out.println("Message sent with ID: " + sendMsgResponse.messageId());
        } catch (Exception e) {
            e.getMessage();
        }
    }

}