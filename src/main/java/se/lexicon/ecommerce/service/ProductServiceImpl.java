package se.lexicon.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import se.lexicon.ecommerce.dto.product.ProductRequest;
import se.lexicon.ecommerce.dto.product.ProductResponse;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductResponse create(ProductRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<ProductResponse> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ProductResponse searchByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByName'");
    }
}
