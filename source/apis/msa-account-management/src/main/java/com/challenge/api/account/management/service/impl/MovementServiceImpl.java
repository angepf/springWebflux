package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.domain.enums.MovementError;
import com.challenge.api.account.management.exception.MovementException;
import com.challenge.api.account.management.repository.MovementRepository;
import com.challenge.api.account.management.service.MovementService;
import com.challenge.api.account.management.service.ReportService;
import com.challenge.api.account.management.service.mapper.MovementMapper;
import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.MovementTypeEnum;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovementServiceImpl implements MovementService {

    MovementRepository movementRepository;
    MovementMapper movementMapper;
    ReportService reportService;

    @Override
    public Mono<PostMovementResponse> createMovement(PostMovementRequest movementRequest) {
        Movement movement = movementMapper.toMovement(movementRequest);
        movement.setDate(LocalDate.now());
        movement.setStatus(true);

        return Mono.just(movement)
                .flatMap(this::validateAndUpdateBalance)
                .flatMap(movementRepository::save)
                .map(movementMapper::toPostMovementResponse)
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(movementResponse -> {
                    log.info("Movement created successfully: {}", movementResponse);
                    reportService.publishReportResponseNew(movement).subscribe();
                })
                .doOnError(error -> log.error("Error creating movement: {}", error.getMessage()))
                .onErrorMap(error -> new MovementException(MovementError.INTERNAL_ERROR, "Failed to create movement: " + error.getMessage()));
    }

    private Mono<Movement> validateAndUpdateBalance(Movement movement) {
        log.info("Validating and updating balance for movement: {}", movement);
        return getLatestBalance(movement.getAccountNumber())
                .flatMap(balance -> {
                    log.info("Latest balance retrieved: {}", balance);
                    BigDecimal value = movement.getValue();

                    if (movement.getType().equals(MovementTypeEnum.CREDIT)) {
                        movement.setBalance(balance.add(value));
                    } else {
                        if (balance.compareTo(value) < 0) {
                            return Mono.error(new MovementException(MovementError.INSUFFICIENT_FUNDS));
                        }
                        movement.setBalance(balance.subtract(value));
                    }
                    log.info("Balance updated successfully: {}", movement);
                    return Mono.just(movement);
                });
    }

    private Mono<BigDecimal> getLatestBalance(Long accountNumber) {
        return movementRepository.findLatestBalanceByAccountNumber(accountNumber)
                .defaultIfEmpty(BigDecimal.ZERO);
    }

    @Override
    public Mono<PostMovementResponse> getMovementById(Long id) {
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new MovementException(MovementError.NOT_FOUND, "Movement not found with id: " + id)))
                .map(movementMapper::toPostMovementResponse)
                .doOnSuccess(movementResponse -> log.info("Movement retrieved successfully: {}", movementResponse))
                .doOnError(error -> log.error("Error retrieving movement: {}", error.getMessage()))
                .onErrorMap(error -> new MovementException(MovementError.INTERNAL_ERROR, "Failed to retrieve movement: " + error.getMessage()));
    }

    @Override
    public Flux<PostMovementResponse> getAllMovements() {
        return movementRepository.findAll()
                .map(movementMapper::toPostMovementResponse)
                .doOnComplete(() -> log.info("All movements retrieved successfully"))
                .doOnError(error -> log.error("Error retrieving all movements: {}", error.getMessage()))
                .onErrorMap(error -> new MovementException(MovementError.INTERNAL_ERROR, "Failed to retrieve all movements: " + error.getMessage()));
    }

    @Override
    public Mono<PostMovementResponse> updateMovement(Long id, PostMovementRequest movementRequest) {
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new MovementException(MovementError.NOT_FOUND, "Movement not found with id: " + id)))
                .flatMap(existingMovement -> {
                    Movement updatedMovement = movementMapper.toMovement(movementRequest);
                    updatedMovement.setId(existingMovement.getId());
                    return movementRepository.save(updatedMovement);
                })
                .map(movementMapper::toPostMovementResponse)
                .doOnSuccess(acc -> log.info("Movement updated successfully: {}", acc))
                .doOnError(error -> log.error("Error updating movement: {}", error.getMessage()))
                .onErrorMap(error -> new MovementException(MovementError.INTERNAL_ERROR, "Failed to update movement: " + error.getMessage()));
    }

    @Override
    public Mono<DeleteResponse> deleteMovement(Long id) {
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new MovementException(MovementError.NOT_FOUND, "Movement not found with id: " + id)))
                .flatMap(existingMovement -> movementRepository.delete(existingMovement)
                        .then(Mono.just(createDeleteResponse(existingMovement.getId()))))
                .doOnSuccess(response -> log.info("Movement deleted successfully: {}", response))
                .doOnError(error -> log.error("Error deleting movement: {}", error.getMessage()))
                .onErrorMap(error -> new MovementException(MovementError.INTERNAL_ERROR, "Failed to delete movement: " + error.getMessage()));
    }

    private DeleteResponse createDeleteResponse(Long movementNumber) {
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Movement successfully deleted.");
        response.setId(movementNumber);
        return response;
    }

}
