package se.lexicon.ecommerce.service;

import se.lexicon.ecommerce.dto.request.CustomerRequestDTO;
import se.lexicon.ecommerce.dto.response.CustomerResponseDTO;

/**
 * Service contract for customer operations.
 */
public interface CustomerService {
    /**
     * Registers a new customer.
     *
     * @param request customer input
     * @return created customer
     */
    public CustomerResponseDTO register(CustomerRequestDTO request);

    /**
     * Finds a customer by id.
     *
     * @param id customer id
     * @return matching customer
     */
    public CustomerResponseDTO findById(Long id);

    /**
     * Updates an existing customer.
     *
     * @param id customer id
     * @param request updated customer input
     * @return updated customer
     */
    public CustomerResponseDTO update(Long id, CustomerRequestDTO request);

    /**
     * Deletes a customer by id.
     *
     * @param id customer id
     */
    public void delete(Long id);
}