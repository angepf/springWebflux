package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.domain.enums.PersonError;
import com.challenge.api.customer.management.exception.PersonException;
import com.challenge.api.customer.management.repository.PersonRepository;
import com.challenge.api.customer.management.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Mono<Person> createPerson(Person person) {
        log.info("Creating new person with id: {}", person.getIdentification());
        return personRepository.insertPerson(person)
                .doOnSuccess(p -> log.info("Person created successfully: {}", p.getIdentification()))
                .onErrorMap(error -> {
                    log.error("Error creating person: {}", error.getMessage(), error);
                    return new PersonException(PersonError.INTERNAL_ERROR, "Failed to create person: " + error.getMessage());
                });
    }

    @Override
    public Mono<Person> getById(String identification) {
        log.info("Fetching person by id: {}", identification);
        return personRepository.findById(identification)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Person not found with id: {}", identification);
                    return Mono.error(new PersonException(PersonError.NOT_FOUND, "Person not found with id: " + identification));
                }))
                .doOnSuccess(person -> log.info("Person found: {}", person.getIdentification()))
                .onErrorMap(error -> {
                    if (error instanceof PersonException) return error;
                    log.error("Error retrieving person: {}", error.getMessage(), error);
                    return new PersonException(PersonError.INTERNAL_ERROR, "Unexpected error retrieving person: " + error.getMessage());
                });
    }

    @Override
    public Mono<Person> updatePerson(String identification, Person person) {
        log.info("Updating person with id: {}", identification);
        return personRepository.findById(identification)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Person not found for update with id: {}", identification);
                    return Mono.error(new PersonException(PersonError.NOT_FOUND, "Cannot update. Person not found with id: " + identification));
                }))
                .flatMap(existingPerson -> {
                    updatePersonFields(existingPerson, person);
                    return personRepository.save(existingPerson)
                            .doOnSuccess(updated -> log.info("Person updated successfully: {}", updated.getIdentification()));
                })
                .onErrorMap(error -> {
                    log.error("Error updating person: {}", error.getMessage(), error);
                    return new PersonException(PersonError.INTERNAL_ERROR, "Failed to update person: " + error.getMessage());
                });
    }

    private void updatePersonFields(Person existingPerson, Person newPerson) {
        existingPerson.setName(newPerson.getName());
        existingPerson.setGender(newPerson.getGender());
        existingPerson.setAddress(newPerson.getAddress());
        existingPerson.setPhone(newPerson.getPhone());
    }

}
