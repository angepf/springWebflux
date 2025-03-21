package com.challenge.api.account.management.util;

import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.service.models.MovementTypeEnum;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovementMockData {

    public static PostMovementRequest getPostMovementRequest() {
        PostMovementRequest movementRequest = new PostMovementRequest();
        movementRequest.id(1L);
        movementRequest.date(LocalDate.now());
        movementRequest.accountNumber(439483L);
        movementRequest.balance(BigDecimal.TEN);
        movementRequest.type(MovementTypeEnum.CREDIT);
        movementRequest.value(BigDecimal.TEN);
        movementRequest.status(true);

        return movementRequest;
    }

    public static Movement getMovement() {
        Movement movement = new Movement();
        movement.setId(1L);
        movement.setDate(LocalDate.now());
        movement.setAccountNumber(439483L);
        movement.setBalance(BigDecimal.TEN);
        movement.setType(MovementTypeEnum.CREDIT);
        movement.setValue(BigDecimal.TEN);
        movement.setStatus(true);

        return movement;
    }

    public static PostMovementResponse getPostMovementResponse() {
        PostMovementResponse movementResponse = new PostMovementResponse();
        movementResponse.date(LocalDate.now());
        movementResponse.accountNumber(439483L);
        movementResponse.balance(BigDecimal.TEN);
        movementResponse.type(MovementTypeEnum.CREDIT);
        movementResponse.value(BigDecimal.TEN);
        movementResponse.status(true);

        return movementResponse;
    }

}
