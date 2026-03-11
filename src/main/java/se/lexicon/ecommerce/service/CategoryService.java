package se.lexicon.ecommerce.service;

import java.util.List;

import se.lexicon.ecommerce.dto.request.CategoryRequestDTO;
import se.lexicon.ecommerce.dto.response.CategoryResponseDTO;

/**
 * Service contract for category operations.
 */
public interface CategoryService {

    /**
     * Creates a category.
     *
     * @param request category input
     * @return created category
     */
    public CategoryResponseDTO create(CategoryRequestDTO request);

    /**
     * Lists all categories.
     *
     * @return categories in storage
     */
    public List<CategoryResponseDTO> findAll();

}
