package se.lexicon.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Product;

/**
 * Repository for {@link Product} persistence and catalog search/filter queries.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds products by category name, case-insensitive.
     *
     * @param categoryName category name
     * @return matching products
     */
    List<Product> findByCategoryNameIgnoreCase(String categoryName);

    /**
     * Finds products within a price range.
     *
     * @param minPrice minimum price
     * @param maxPrice maximum price
     * @return matching products
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Finds products whose name contains the keyword, case-insensitive.
     *
     * @param keyword search keyword
     * @return matching products
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);

    /**
     * Finds products with price lower than the given value.
     *
     * @param price price threshold
     * @return matching products
     */
    List<Product> findByPriceLessThan(BigDecimal price);

    /**
     * Finds all products ordered by ascending price.
     *
     * @return ordered products
     */
    List<Product> findByOrderByPriceAsc();

    /**
     * Finds all products ordered by descending price.
     *
     * @return ordered products
     */
    List<Product> findByOrderByPriceDesc();

    /**
     * Finds products by category id.
     *
     * @param categoryId category id
     * @return matching products
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Counts products in a category.
     *
     * @param categoryId category id
     * @return product count
     */
    long countByCategoryId(Long categoryId);

    /**
     * Checks whether a product exists by name (case-insensitive) and category.
     *
     * @param name       product name
     * @param categoryId category id
     * @return true if matching product exists
     */
    boolean existsByNameIgnoreCaseAndCategoryId(String name, Long categoryId);
}