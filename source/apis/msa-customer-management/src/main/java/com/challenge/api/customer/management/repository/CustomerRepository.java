package com.challenge.api.customer.management.repository;

import com.challenge.api.customer.management.domain.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    @Query("INSERT INTO S_CHALLENGE.T_CUSTOMERS (identification, password, status) VALUES (:#{#customer.identification}, :#{#customer.password}, :#{#customer.status}) RETURNING *")
    Mono<Customer> insertCustomer(Customer customer);

}