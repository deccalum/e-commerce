package se.lexicon.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a category.
 *
 * @param name category name
 */
public record CategoryRequestDTO(

    @NotBlank
    @Size(max = 50)
    String name
) {}

