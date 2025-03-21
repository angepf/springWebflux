package com.challenge.api.account.management.service.impl;

import com.challenge.api.account.management.exception.MovementException;
import com.challenge.api.account.management.repository.MovementRepository;
import com.challenge.api.account.management.service.ReportService;
import com.challenge.api.account.management.service.mapper.MovementMapper;
import com.challenge.api.account.management.service.models.DeleteResponse;
import com.challenge.api.account.management.service.models.PostMovementResponse;
import com.challenge.api.account.management.util.Constants;
import com.challenge.api.account.management.util.MovementMockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovementServiceImplTest {

    @Mock
    MovementRepository movementRepository;

    @Mock
    MovementMapper movementMapper;

    @Mock
    ReportService reportService;

    @InjectMocks
    MovementServiceImpl movementService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRequestWhenCreateMovementThenMovementIsCreated() {
        when(movementMapper.toMovement(any()))
                .thenReturn(MovementMockData.getMovement());
        when(movementRepository.save(any()))
                .thenReturn(Mono.just(MovementMockData.getMovement()));
        when(movementMapper.toPostMovementResponse(any()))
                .thenReturn(MovementMockData.getPostMovementResponse());
        when(movementRepository.findLatestBalanceByAccountNumber(anyLong()))
                .thenReturn(Mono.empty());
        when(reportService.publishReportResponseNew(any()))
                .thenReturn(Mono.empty());

        Mono<PostMovementResponse> responseMono = movementService
                .createMovement(MovementMockData.getPostMovementRequest());

        PostMovementResponse response = responseMono.block();
        verify(movementRepository).save(MovementMockData.getMovement());
        assertNotNull(response);
    }

    @Test
    void givenErrorWhenCreateMovementThenExceptionThrown() {
        when(movementMapper.toMovement(any()))
                .thenReturn(MovementMockData.getMovement());
        when(movementRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));
        when(movementRepository.findLatestBalanceByAccountNumber(anyLong()))
                .thenReturn(Mono.empty());
        when(reportService.publishReportResponseNew(any()))
                .thenReturn(Mono.empty());

        Mono<PostMovementResponse> responseMono = movementService
                .createMovement(MovementMockData.getPostMovementRequest());

        MovementException exception = assertThrows(MovementException.class, responseMono::block);
        assertEquals("Failed to create movement: Database error", exception.getMessage());
    }

    @Test
    void givenMovementsExistWhenGetAllMovementsThenMovementsRetrieved() {
        when(movementRepository.findAll())
                .thenReturn(Flux.just(MovementMockData.getMovement()));
        when(movementMapper.toPostMovementResponse(any()))
                .thenReturn(MovementMockData.getPostMovementResponse());

        Flux<PostMovementResponse> responseFlux = movementService.getAllMovements();

        List<PostMovementResponse> responses = responseFlux.collectList().block();
        verify(movementRepository).findAll();
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals(MovementMockData.getPostMovementResponse(), responses.get(0));
    }

    @Test
    void givenErrorWhenGetAllMovementsThenExceptionThrown() {
        when(movementRepository.findAll())
                .thenReturn(Flux.error(new RuntimeException("Database error")));

        Flux<PostMovementResponse> responseFlux = movementService.getAllMovements();

        MovementException exception = assertThrows(MovementException.class, () -> responseFlux.collectList().block());
        assertEquals("Failed to retrieve all movements: Database error", exception.getMessage());
    }

    @Test
    void givenMovementExistsWhenGetMovementByIdThenMovementRetrieved() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.just(MovementMockData.getMovement()));
        when(movementMapper.toPostMovementResponse(any()))
                .thenReturn(MovementMockData.getPostMovementResponse());

        Mono<PostMovementResponse> responseMono = movementService
                .getMovementById(Constants.MOVEMENT_ID);

        PostMovementResponse response = responseMono.block();
        verify(movementRepository).findById(Constants.MOVEMENT_ID);
        assertNotNull(response);
        assertEquals(MovementMockData.getPostMovementResponse(), response);
    }

    @Test
    void givenMovementNotExistsWhenGetMovementByIdThenExceptionThrown() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        Mono<PostMovementResponse> responseMono = movementService
                .getMovementById(Constants.MOVEMENT_ID);

        MovementException exception = assertThrows(MovementException.class, responseMono::block);
        assertEquals("Failed to retrieve movement: Movement not found, with id: [1]", exception.getMessage());
    }

    @Test
    void givenExistingMovementWhenUpdateMovementThenMovementUpdated() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.just(MovementMockData.getMovement()));
        when(movementMapper.toMovement(any()))
                .thenReturn(MovementMockData.getMovement());
        when(movementRepository.save(any()))
                .thenReturn(Mono.just(MovementMockData.getMovement()));
        when(movementMapper.toPostMovementResponse(any()))
                .thenReturn(MovementMockData.getPostMovementResponse());

        PostMovementResponse response = movementService
                .updateMovement(Constants.MOVEMENT_ID, MovementMockData.getPostMovementRequest())
                .block();

        verify(movementRepository).save(MovementMockData.getMovement());
        assertNotNull(response);
    }

    @Test
    void givenNonExistingMovementWhenUpdateMovementThenExceptionThrown() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        Mono<PostMovementResponse> responseMono = movementService
                .updateMovement(Constants.MOVEMENT_ID, MovementMockData.getPostMovementRequest());

        MovementException exception = assertThrows(MovementException.class, responseMono::block);
        assertEquals("Failed to update movement: Movement not found with id: " + Constants.MOVEMENT_ID, exception.getMessage());
    }

    @Test
    void givenMovementExistsWhenDeleteMovementThenMovementDeleted() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.just(MovementMockData.getMovement()));
        when(movementRepository.delete(any()))
                .thenReturn(Mono.empty());

        Mono<DeleteResponse> responseMono = movementService
                .deleteMovement(Constants.MOVEMENT_ID);

        responseMono.block();
        verify(movementRepository).delete(MovementMockData.getMovement());
    }

    @Test
    void givenNonExistingMovementWhenDeleteMovementThenExceptionThrown() {
        when(movementRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        Mono<DeleteResponse> responseMono = movementService
                .deleteMovement(Constants.MOVEMENT_ID);

        MovementException exception = assertThrows(MovementException.class, responseMono::block);
        assertEquals("Failed to delete movement: Movement not found, with id: [1]", exception.getMessage());
    }

}