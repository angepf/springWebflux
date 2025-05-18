package com.challenge.api.customer.management.service;

import com.challenge.api.customer.management.domain.Person;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<Person> createPerson(Person person);

    Mono<Person> getById(String id);

    Mono<Person> updatePerson(String id, Person person);

}