package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.service.PersonService;
import com.challenge.api.customer.management.service.SqsService;
import com.challenge.api.customer.management.service.mapper.CustomerMapper;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.r2dbc.url=r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.r2dbc.username=sa",
        "spring.r2dbc.password=password",
        "aws.sqs.access-key=test",
        "aws.sqs.secret-key=secret",
        "aws.sqs.region=us-east-1",
        "aws.sqs.report-queue=report-queue",
        "spring.cloud.compatibility-verifier.enabled=false",
        "eureka.client.enabled=false"
})
public class CustomerServiceImplIntegrationTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @MockBean
    private SqsService sqsService;

    @Autowired
    private PersonService personService;

    @Test
    void givenTwoCustomers_whenGetAllCustomers_thenReturnTwoCustomers() {
        Flux<PostCustomerResponse> responseFlux = customerService.getAllCustomers();

        List<PostCustomerResponse> customers = responseFlux.collectList().block();
        assertNotNull(customers, "The response should not be null");
        assertEquals(3, customers.size(), "The number of customers should be 2");
    }

}