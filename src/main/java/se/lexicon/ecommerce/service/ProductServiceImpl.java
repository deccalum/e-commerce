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
import se.lexicon.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

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

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

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
