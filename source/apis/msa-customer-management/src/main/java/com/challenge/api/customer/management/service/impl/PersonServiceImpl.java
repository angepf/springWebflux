package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.repository.PersonRepository;
import com.challenge.api.customer.management.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Mono<Person> createPerson(Person person) {
        return personRepository.insertPerson(person);
    }

    @Override
    public Mono<Person> getById(String identification) {
        return personRepository.findById(identification)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> updatePerson(String identification, Person person) {
        return personRepository.findById(identification)
                .flatMap(existingPerson -> {
                    existingPerson.setName(person.getName());
                    existingPerson.setGender(person.getGender());
                    existingPerson.setAddress(person.getAddress());
                    existingPerson.setPhone(person.getPhone());
                    return personRepository.save(existingPerson);
                });
    }

    @Override
    public Mono<Void> deletePerson(String id) {
        return personRepository.deleteById(id);
    }
}
