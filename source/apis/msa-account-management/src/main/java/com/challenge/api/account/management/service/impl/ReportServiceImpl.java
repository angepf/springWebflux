package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.domain.Account;
import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.domain.enums.ReportError;
import com.challenge.api.account.management.exception.ReportException;
import com.challenge.api.account.management.repository.AccountRepository;
import com.challenge.api.account.management.repository.MovementRepository;
import com.challenge.api.account.management.service.CustomerService;
import com.challenge.api.account.management.service.ReportService;
import com.challenge.api.account.management.service.models.AccountReportResponse;
import com.challenge.api.account.management.service.models.PostReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImpl implements ReportService {

    AccountRepository accountRepository;
    MovementRepository movementRepository;
    CustomerService customerService;
    SqsServiceImpl sqsServiceImpl;

    private PostReportResponse generateReportResponse(Movement movement, Account account, String customer){
        PostReportResponse reportResponse = new PostReportResponse();
        reportResponse.setDate(movement.getDate());
        reportResponse.setCustomerName(customer);
        reportResponse.setAccountNumber(account.getAccountNumber());
        reportResponse.setTypeAccount(account.getType());
        reportResponse.setInitialBalance(account.getInitialBalance());
        reportResponse.setStatus(account.getStatus());
        reportResponse.setValue(movement.getValue());
        reportResponse.setTypeMovement(movement.getType());
        reportResponse.setBalance(movement.getBalance());

        return reportResponse;
    }

    @Override
    public Mono<AccountReportResponse> generateReport(String customerId, LocalDate startDate, LocalDate endDate) {
        return accountRepository.findByCustomerId(customerId)
                .next()
                .switchIfEmpty(Mono.error(new ReportException(ReportError.ACCOUNT_NOT_FOUND, customerId)))
                .flatMap(account ->
                        customerService.getCustomerById(account.getCustomerId())
                                .switchIfEmpty(Mono.error(new ReportException(ReportError.CUSTOMER_NOT_FOUND, customerId)))
                                .flatMap(customer ->
                                        movementRepository.findByAccountNumberAndDateBetween(account.getAccountNumber(), startDate, endDate)
                                                .publishOn(Schedulers.boundedElastic())
                                                .map(movement -> generateReportResponse(movement, account, customer.getName()))
                                                .collectList()
                                                .map(transactions -> {
                                                    AccountReportResponse reportResponse = new AccountReportResponse();
                                                    reportResponse.setTransactions(transactions);
                                                    return reportResponse;
                                                })
                                )
                );
    }

    public Mono<Void> publishReportResponseNew(Movement movement) {
        return accountRepository.findById(movement.getAccountNumber())
                .flatMap(account -> {
                    sqsServiceImpl.sendReport(generateReportResponse(movement, account, account.getCustomerId()));
                    return Mono.empty();
                })
                .doOnSuccess(account -> log.info("Report published successfully")).then();
    }

}