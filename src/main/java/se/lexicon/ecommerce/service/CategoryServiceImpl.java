package se.lexicon.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import se.lexicon.ecommerce.dto.request.CategoryRequestDTO;
import se.lexicon.ecommerce.dto.response.CategoryResponseDTO;
import se.lexicon.ecommerce.exception.DuplicateResourceException;
import se.lexicon.ecommerce.exception.InvalidRequestException;
import se.lexicon.ecommerce.model.Category;
import se.lexicon.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("Request must not be null");
        }

        return Optional.of(request.name())
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .filter(name -> !categoryRepository.existsByNameIgnoreCase(name))
                .map(name -> {
                    Category c = new Category();
                    c.setName(name);
                    return c;
                })
                .map(categoryRepository::save)
                .map(saved -> new CategoryResponseDTO(saved.getId(), saved.getName()))
                .orElseThrow(() -> new DuplicateResourceException(
                        "Category already exists: " + request.name()));
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponseDTO(c.getId(), c.getName()))
                .toList();
    }

}
