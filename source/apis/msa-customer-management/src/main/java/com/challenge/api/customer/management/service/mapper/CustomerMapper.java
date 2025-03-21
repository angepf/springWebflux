package com.challenge.api.customer.management.service.mapper;

import com.challenge.api.customer.management.domain.Customer;
import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.service.models.PostCustomerRequest;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;

public interface CustomerMapper {

    Person toPerson(PostCustomerRequest postCustomerRequest);

    Customer toCustomer(PostCustomerRequest postCustomerRequest);

    PostCustomerResponse toPostCustomerResponse(Customer customer, Person person);

}
