package se.lexicon.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import se.lexicon.ecommerce.dto.customer.CustomerRequest;
import se.lexicon.ecommerce.dto.customer.CustomerResponse;
import se.lexicon.ecommerce.exception.DuplicateResourceException;
import se.lexicon.ecommerce.exception.InvalidRequestException;
import se.lexicon.ecommerce.exception.ResourceNotFoundException;
import se.lexicon.ecommerce.mapper.CustomerMapper;
import se.lexicon.ecommerce.model.Address;
import se.lexicon.ecommerce.repository.CustomerRepository;

/**
 * Service implementation for managing customers.
 * Handles customer registration, retrieval, and updates.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Registers a new customer.
     *
     * @param request the customer registration request
     * @return the registered customer response
     * @throws InvalidRequestException if the request is null
     * @throws DuplicateResourceException if a customer with the same email already exists
     */
    @Override
    public CustomerResponse register(CustomerRequest request) {
        return Optional.ofNullable(request)
                .filter(req -> !customerRepository.existsByEmail(req.email()))
                .map(customerMapper::toEntity)
                .map(customerRepository::save)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> request == null
                        ? new InvalidRequestException("Request must not be null")
                        : new DuplicateResourceException("Customer with email already exists: " + request.email()));
    }

    /**
     * Finds a customer by their ID.
     *
     * @param id the ID of the customer
     * @return the customer response
     * @throws InvalidRequestException if the ID is null or not positive
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public CustomerResponse findById(Long id) {
        return Optional.ofNullable(id)
                .filter(value -> value > 0)
                .map(customerRepository::findById)
                .orElseThrow(() -> new InvalidRequestException("Id must be a positive number"))
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Updates an existing customer.
     *
     * @param id the ID of the customer to update
     * @param request the customer update request
     * @return the updated customer response
     * @throws InvalidRequestException if the ID is null or not positive, or if the request is null
     * @throws ResourceNotFoundException if the customer is not found
     * @throws DuplicateResourceException if a customer with the same email already exists
     */
    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        Optional.ofNullable(request)
                .orElseThrow(() -> new InvalidRequestException("Request must not be null"));

        Long validId = Optional.ofNullable(id)
                .filter(value -> value > 0)
                .orElseThrow(() -> new InvalidRequestException("Id must be a positive number"));

        return customerRepository.findById(validId)
                .map(existing -> {
                    if (!existing.getEmail().equalsIgnoreCase(request.email())
                            && customerRepository.existsByEmail(request.email())) {
                        throw new DuplicateResourceException(
                                "Customer with email already exists: " + request.email());
                    }
                    existing.setFirstName(request.firstName());
                    existing.setLastName(request.lastName());
                    existing.setEmail(request.email());

                    Address address = Optional.ofNullable(existing.getAddress())
                            .orElseGet(() -> {
                                Address newAddress = new Address();
                                existing.setAddress(newAddress);
                                return newAddress;
                            });

                    address.setStreet(request.street());
                    address.setCity(request.city());
                    address.setZipCode(request.zipCode());
                    return existing;
                })
                .map(customerRepository::save)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + validId));
    }
}