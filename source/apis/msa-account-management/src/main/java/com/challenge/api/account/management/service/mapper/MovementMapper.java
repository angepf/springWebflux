package com.challenge.api.account.management.service.mapper;

import com.challenge.api.account.management.domain.Movement;
import com.challenge.api.account.management.service.models.PostMovementRequest;
import com.challenge.api.account.management.service.models.PostMovementResponse;

public interface MovementMapper {

    Movement toMovement(PostMovementRequest postMovementRequest);

    PostMovementResponse toPostMovementResponse(Movement movement);

}
