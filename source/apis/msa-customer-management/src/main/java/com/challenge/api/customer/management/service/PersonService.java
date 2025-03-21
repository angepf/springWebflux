package com.challenge.api.customer.management.service;

import com.challenge.api.customer.management.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<Person> createPerson(Person person);

    Mono<Person> getById(String id);

    //Mono<Person> getPersonByIdentification(String identification);

    Flux<Person> getAllPersons();

    Mono<Person> updatePerson(String id, Person person);

    Mono<Void> deletePerson(String id);
}