package com.challenge.api.account.management.service;

import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    Mono<PostMovementResponse> createMovement(PostMovementRequest movementRequest);

    Mono<PostMovementResponse> getMovementById(Long movementNumber);

    Flux<PostMovementResponse> getAllMovements();

    Mono<PostMovementResponse> updateMovement(Long movementNumber, PostMovementRequest movementRequest);

    Mono<DeleteResponse> deleteMovement(Long movementNumber);
}