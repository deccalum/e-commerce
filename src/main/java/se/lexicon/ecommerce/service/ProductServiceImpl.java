package se.lexicon.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;
import se.lexicon.ecommerce.exception.DuplicateResourceException;
import se.lexicon.ecommerce.exception.InvalidRequestException;
import se.lexicon.ecommerce.exception.ResourceNotFoundException;
import se.lexicon.ecommerce.mapper.ProductMapper;
import se.lexicon.ecommerce.model.Product;
import se.lexicon.ecommerce.repository.ProductRepository;

/**
 * Default {@link ProductService} implementation for
 * {@link Product} operations.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Creates and stores a product from request input.
     *
        * @param request input {@link ProductRequestDTO}
        * @return created {@link ProductResponseDTO}
     */
    @Override
    public ProductResponseDTO create(ProductRequestDTO request) {
        return Optional.ofNullable(request)
                .map(productMapper::toEntity)
                .map(productRepository::save)
                .map(productMapper::toResponse)
                .orElseThrow(() -> request == null
                        ? new InvalidRequestException("Request must not be null")
                        : new DuplicateResourceException("Product with name already exists: " + request.name()));
    }

    /**
     * Lists all products.
     *
        * @return all products as {@link ProductResponseDTO} values
     */
    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    /**
     * Finds the first product matching a name fragment.
     *
     * @param name search text
        * @return matching {@link ProductResponseDTO}
     */
    @Override
    public ProductResponseDTO searchByName(String name) {
        return Optional.ofNullable(name)
                .filter(n -> !n.isBlank())
                .map(productRepository::findByNameContainingIgnoreCase)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + name));
    }
}
