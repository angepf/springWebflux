package com.challenge.api.account.management.controller;

import com.challenge.api.account.management.service.MovementService;
import com.challenge.api.account.management.service.models.*;
import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MovementController implements MovementsApi {

    MovementService movementService;

    @Override
    public Mono<ResponseEntity<Flux<PostMovementResponse>>> getAllMovements(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(movementService.getAllMovements()))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Override
    public Mono<ResponseEntity<PostMovementResponse>> getMovementById(Long movementId, ServerWebExchange exchange) {
        return movementService.getMovementById(movementId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<PostMovementResponse>> createMovement(PostMovementRequest postMovementRequest, ServerWebExchange exchange) {
        return movementService.createMovement(postMovementRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Override
    public Mono<ResponseEntity<PostMovementResponse>> updateMovementById(Long movementId, PostMovementRequest postMovementRequest, ServerWebExchange exchange) {
        return movementService.updateMovement(movementId, postMovementRequest)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<DeleteResponse>> deleteMovementById(Long movementId, ServerWebExchange exchange) {
        return movementService.deleteMovement(movementId)
                .map(deleteResponse -> {
                    if ("Customer successfully deleted.".equals(deleteResponse.getMessage())) {
                        return ResponseEntity.ok(deleteResponse);
                    } else {
                        return ResponseEntity.status(404).body(deleteResponse);
                    }
                });
    }





}