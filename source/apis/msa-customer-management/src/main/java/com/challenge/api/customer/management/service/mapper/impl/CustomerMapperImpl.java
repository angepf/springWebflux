package com.challenge.api.customer.management.service.mapper.impl;

import com.challenge.api.customer.management.domain.Customer;
import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.service.mapper.CustomerMapper;
import com.challenge.api.customer.management.service.models.PostCustomerRequest;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Person toPerson(PostCustomerRequest postCustomerRequest) {
        return Person.builder()
                .identification(postCustomerRequest.getIdentification())
                .name(postCustomerRequest.getName())
                .gender(postCustomerRequest.getGender())
                .address(postCustomerRequest.getAddress())
                .phone(postCustomerRequest.getPhone())
                .build();
    }

    @Override
    public Customer toCustomer(PostCustomerRequest postCustomerRequest) {
        return Customer.builder()
                .identification(postCustomerRequest.getIdentification())
                .password(postCustomerRequest.getPassword())
                .status(postCustomerRequest.getStatus())
                .build();
    }

    @Override
    public PostCustomerResponse toPostCustomerResponse(Customer customer, Person person) {
        PostCustomerResponse postCustomerResponse = new PostCustomerResponse();
        postCustomerResponse.setIdentification(customer.getIdentification());
        postCustomerResponse.setStatus(customer.getStatus());
        postCustomerResponse.setName(person.getName());
        postCustomerResponse.setGender(person.getGender());
        postCustomerResponse.setAddress(person.getAddress());
        postCustomerResponse.setPhone(person.getPhone());

        return postCustomerResponse;
    }
}
