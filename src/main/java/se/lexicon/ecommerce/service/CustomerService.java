package se.lexicon.ecommerce.service;

import se.lexicon.ecommerce.dto.customer.CustomerRequest;
import se.lexicon.ecommerce.dto.customer.CustomerResponse;

public interface CustomerService {
    public CustomerResponse register(CustomerRequest request);

    public CustomerResponse findById(Long id);

    public CustomerResponse update(Long id, CustomerRequest request);
}