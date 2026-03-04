package se.lexicon.ecommerce.service;

import java.util.List;

import se.lexicon.ecommerce.dto.product.ProductRequest;
import se.lexicon.ecommerce.dto.product.ProductResponse;

public interface ProductService {
    public ProductResponse create(ProductRequest request);
    public List<ProductResponse> findAll();
    public ProductResponse searchByName(String name);
}