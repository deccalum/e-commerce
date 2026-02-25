package se.lexicon.ecommerce.repository;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, BigInteger> {

    Category findByNameIgnoreCase(String name);
    List<Category> findByNameContainingIgnoreCase(String keyword);
    boolean existsByNameIgnoreCase(String name);
}
