package se.lexicon.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Category;

/**
 * Repository for {@link Category} persistence and category queries.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds a category by name, case-insensitive.
     *
     * @param name category name
     * @return matching category or null
     */
    Category findByNameIgnoreCase(String name);

    /**
     * Finds categories whose names contain the given keyword, case-insensitive.
     *
     * @param keyword keyword to search for
     * @return matching categories
     */
    List<Category> findByNameContainingIgnoreCase(String keyword);

    /**
     * Checks whether a category exists by name, case-insensitive.
     *
     * @param name category name
     * @return true if a matching category exists
     */
    boolean existsByNameIgnoreCase(String name);
}
