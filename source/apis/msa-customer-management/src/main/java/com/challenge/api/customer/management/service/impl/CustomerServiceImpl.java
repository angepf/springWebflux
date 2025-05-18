package com.challenge.api.customer.management.service.impl;

import com.challenge.api.customer.management.domain.Customer;
import com.challenge.api.customer.management.domain.Person;
import com.challenge.api.customer.management.domain.enums.CustomerError;
import com.challenge.api.customer.management.exception.CustomerException;
import com.challenge.api.customer.management.repository.CustomerRepository;
import com.challenge.api.customer.management.service.CustomerService;
import com.challenge.api.customer.management.service.PersonService;
import com.challenge.api.customer.management.service.mapper.CustomerMapper;
import com.challenge.api.customer.management.util.Constants;
import com.challenge.api.customer.management.service.models.DeleteCustomerResponse;
import com.challenge.api.customer.management.service.models.PostCustomerRequest;
import com.challenge.api.customer.management.service.models.PostCustomerResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    PersonService personService;

    @Override
    public Flux<PostCustomerResponse> getAllCustomers() {
        String methodName = "getAllCustomers";
        log.info(Constants.INIT_METHOD, methodName);

        return customerRepository.findAll()
                .flatMap(customer -> personService.getById(customer.getIdentification())
                        .map(person -> customerMapper.toPostCustomerResponse(customer, person)))
                .onErrorMap(ex -> {
                    log.error("Unexpected error in getAllCustomers: {}", ex.getMessage(), ex);
                    return new CustomerException(CustomerError.INTERNAL_ERROR, ex.getMessage());
                });
    }

    @Override
    public Mono<PostCustomerResponse> getCustomerById(String identification) {
        String methodName = "getCustomerById";
        log.info(Constants.INIT_METHOD_PARAMETERS, methodName, "identification", identification);

        return personService.getById(identification)
                .flatMap(person -> customerRepository.findById(person.getIdentification())
                        .map(customer -> customerMapper.toPostCustomerResponse(customer, person)))
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, "Customer not found: " + identification)))
                .onErrorMap(ex -> {
                    log.error("Unexpected error in getCustomerById: {}", ex.getMessage(), ex);
                    if (ex instanceof CustomerException) return ex;
                    return new CustomerException(CustomerError.INTERNAL_ERROR, ex.getMessage());
                });
    }

    @Override
    public Mono<PostCustomerResponse> createCustomer(PostCustomerRequest customerRequest) {
        String methodName = "createCustomer";
        log.info(Constants.INIT_METHOD_PARAMETERS, methodName, "customerRequest", customerRequest);

        return getOrCreatePerson(customerRequest)
                .flatMap(person -> createAndSaveCustomer(customerRequest, person))
                .onErrorMap(ex -> {
                    log.error("Unexpected error in createCustomer: {}", ex.getMessage(), ex);
                    if (ex instanceof CustomerException) return ex;
                    return new CustomerException(CustomerError.INTERNAL_ERROR, ex.getMessage());
                });
    }

    private Mono<Person> getOrCreatePerson(PostCustomerRequest customerRequest) {
        return personService.getById(customerRequest.getIdentification())
                .switchIfEmpty(personService.createPerson(customerMapper.toPerson(customerRequest)));
    }

    private Mono<PostCustomerResponse> createAndSaveCustomer(PostCustomerRequest customerRequest, Person person) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.setIdentification(person.getIdentification());

        return customerRepository.findById(customer.getIdentification())
                .switchIfEmpty(customerRepository.insertCustomer(customer))
                .map(savedCustomer -> customerMapper.toPostCustomerResponse(savedCustomer, person));
    }

    @Override
    public Mono<PostCustomerResponse> updateCustomer(String identification, PostCustomerRequest customerRequest) {
        String methodName = "updateCustomer";
        log.info(Constants.INIT_METHOD_PARAMETERS, methodName, "identification", identification);

        return personService.getById(identification)
                .flatMap(existingPerson -> updatePersonAndCustomer(existingPerson, customerRequest))
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, "Person not found: " + identification)))
                .onErrorMap(ex -> {
                    log.error("Unexpected error in updateCustomer: {}", ex.getMessage(), ex);
                    if (ex instanceof CustomerException) return ex;
                    return new CustomerException(CustomerError.INTERNAL_ERROR, ex.getMessage());
                });
    }

    private Mono<PostCustomerResponse> updatePersonAndCustomer(Person existingPerson, PostCustomerRequest customerRequest) {
        return updatePersonRecord(existingPerson, customerRequest)
                .flatMap(updatedPerson -> updateCustomerRecord(updatedPerson, customerRequest));
    }

    private Mono<Person> updatePersonRecord(Person existingPerson, PostCustomerRequest customerRequest) {
        Person updatedPerson = customerMapper.toPerson(customerRequest);
        return personService.updatePerson(existingPerson.getIdentification(), updatedPerson);
    }

    private Mono<PostCustomerResponse> updateCustomerRecord(Person updatedPerson, PostCustomerRequest customerRequest) {
        return customerRepository.findById(updatedPerson.getIdentification())
                .flatMap(existingCustomer -> {
                    Customer updatedCustomer = customerMapper.toCustomer(customerRequest);
                    return customerRepository.save(updatedCustomer)
                            .map(savedCustomer -> customerMapper.toPostCustomerResponse(savedCustomer, updatedPerson));
                })
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, "Customer not found: " + updatedPerson.getIdentification())));
    }

    @Override
    public Mono<DeleteCustomerResponse> deleteCustomer(String identification) {
        String methodName = "deleteCustomer";
        log.info(Constants.INIT_METHOD_PARAMETERS, methodName, "identification", identification);

        return personService.getById(identification)
                .flatMap(this::deleteCustomerByPerson)
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, "Customer not found: " + identification)))
                .onErrorMap(ex -> {
                    log.error("Unexpected error in deleteCustomer: {}", ex.getMessage(), ex);
                    if (ex instanceof CustomerException) return ex;
                    return new CustomerException(CustomerError.INTERNAL_ERROR, ex.getMessage());
                });
    }

    private Mono<DeleteCustomerResponse> deleteCustomerByPerson(Person person) {
        return customerRepository.findById(person.getIdentification())
                .flatMap(customer -> customerRepository.delete(customer)
                        .thenReturn(createDeleteResponse("Customer successfully deleted.", customer.getIdentification())))
                .switchIfEmpty(Mono.error(new CustomerException(CustomerError.NOT_FOUND, "Customer not found: " + person.getIdentification())));
    }

    private DeleteCustomerResponse createDeleteResponse(String message, String customerId) {
        DeleteCustomerResponse response = new DeleteCustomerResponse();
        response.setMessage(message);
        response.setCustomerId(customerId);
        return response;
    }

}
