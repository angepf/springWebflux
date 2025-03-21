package com.challenge.api.account.management.service.mapper.impl;

import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.service.mapper.MovementMapper;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;
import org.springframework.stereotype.Component;

@Component
public class MovementMapperImpl implements MovementMapper {


    @Override
    public Movement toMovement(PostMovementRequest postMovementRequest) {
        return Movement.builder()
                .accountNumber(postMovementRequest.getAccountNumber())
                .type(postMovementRequest.getType())
                .value(postMovementRequest.getValue())
                .build();
    }

    @Override
    public PostMovementResponse toPostMovementResponse(Movement movement) {
        PostMovementResponse movementResponse = new PostMovementResponse();
        movementResponse.setAccountNumber(movement.getAccountNumber());
        movementResponse.setType(movement.getType());
        movementResponse.setValue(movement.getValue());
        movementResponse.setBalance(movement.getBalance());
        movementResponse.setDate(movement.getDate());
        movementResponse.setStatus(movement.getStatus());

        return movementResponse;
    }
}
