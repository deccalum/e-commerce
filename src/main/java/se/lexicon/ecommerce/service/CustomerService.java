package se.lexicon.ecommerce.service;

import se.lexicon.ecommerce.dto.request.CustomerRequestDTO;
import se.lexicon.ecommerce.dto.response.CustomerResponseDTO;

public interface CustomerService {
    public CustomerResponseDTO register(CustomerRequestDTO request);

    public CustomerResponseDTO findById(Long id);

    public CustomerResponseDTO update(Long id, CustomerRequestDTO request);

    public void delete(Long id);
}