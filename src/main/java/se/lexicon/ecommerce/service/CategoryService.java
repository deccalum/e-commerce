package se.lexicon.ecommerce.service;

import java.util.List;

import se.lexicon.ecommerce.dto.request.CategoryRequestDTO;
import se.lexicon.ecommerce.dto.response.CategoryResponseDTO;

public interface CategoryService {

    public CategoryResponseDTO create(CategoryRequestDTO request);

    public List<CategoryResponseDTO> findAll();

}
