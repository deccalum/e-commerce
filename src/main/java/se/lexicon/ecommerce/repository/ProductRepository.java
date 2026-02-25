package se.lexicon.ecommerce.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {

    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByOrderByPriceAsc();
    List<Product> findByOrderByPriceDesc();
    List<Product> findByCategoryId(BigInteger categoryId);
    long countByCategoryId(BigInteger categoryId);
}