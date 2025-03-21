package com.challenge.api.customer.management.service;

public interface SqsService {

    void listenForNewReports(String sqsMessage);

}