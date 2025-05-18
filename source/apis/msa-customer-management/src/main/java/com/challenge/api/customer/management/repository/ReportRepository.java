package com.challenge.api.customer.management.repository;

import com.challenge.api.customer.management.domain.Report;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface ReportRepository extends ReactiveCrudRepository<Report, Long> {

    Flux<Report> findByCustomerIdAndDateBetween(String customerId, LocalDate starDate, LocalDate endDate);
    
}