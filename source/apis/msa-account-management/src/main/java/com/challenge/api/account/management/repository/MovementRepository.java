package com.challenge.api.account.management.repository;

import com.challenge.api.account.management.domain.Movement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

    @Query("SELECT m.balance FROM S_CHALLENGE.T_MOVEMENTS m WHERE m.account_number = :accountNumber ORDER BY m.id DESC LIMIT 1")
    Mono<BigDecimal> findLatestBalanceByAccountNumber(Long accountNumber);

    Flux<Movement> findByAccountNumberAndDateBetween(Long accountNumber, LocalDate dateAfter, LocalDate dateBefore);

}