package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.domain.enums.PersonError;
import com.challenge.api.customer.management.exception.PersonException;
import com.challenge.api.customer.management.repository.PersonRepository;
import com.challenge.api.customer.management.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Mono<Person> createPerson(Person person) {
        return personRepository.insertPerson(person)
                .doOnSuccess(p -> {})
                .doOnError(error -> {
                    throw new PersonException(PersonError.INTERNAL_ERROR, "Failed to create person: " + error.getMessage());
                });
    }

    @Override
    public Mono<Person> getById(String identification) {
        return personRepository.findById(identification)
                .switchIfEmpty(Mono.error(new PersonException(PersonError.NOT_FOUND, "Person not found with id: " + identification)));
    }

    @Override
    public Mono<Person> updatePerson(String identification, Person person) {
        return personRepository.findById(identification)
                .switchIfEmpty(Mono.error(new PersonException(PersonError.NOT_FOUND, "Cannot update. Person not found with id: " + identification)))
                .flatMap(existingPerson -> {
                    existingPerson.setName(person.getName());
                    existingPerson.setGender(person.getGender());
                    existingPerson.setAddress(person.getAddress());
                    existingPerson.setPhone(person.getPhone());
                    return personRepository.save(existingPerson);
                })
                .onErrorMap(error -> new PersonException(PersonError.INTERNAL_ERROR, "Failed to update person: " + error.getMessage()));
    }

}
