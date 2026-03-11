package se.lexicon.ecommerce.service;

import java.util.List;

import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;

/**
 * Service interface for managing products in the e-commerce application.
 */
public interface ProductService {
    public ProductResponseDTO create(ProductRequestDTO request);

    public List<ProductResponseDTO> findAll();

    public ProductResponseDTO searchByName(String name);
}