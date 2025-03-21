package com.challenge.api.customer.management.repository;

import com.challenge.api.customer.management.domain.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

    @Query("INSERT INTO S_CHALLENGE.T_PERSONS (identification, name, gender, address, phone) VALUES (:#{#person.identification}, :#{#person.name}, :#{#person.gender}, :#{#person.address}, :#{#person.phone}) RETURNING *")
    Mono<Person> insertPerson(Person person);

}