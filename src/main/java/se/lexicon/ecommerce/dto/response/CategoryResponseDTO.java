package se.lexicon.ecommerce.dto.response;

/**
 * Response DTO for exposing {@link se.lexicon.ecommerce.model.Category} data.
 *
 * @param id category id
 * @param name category name
 */
public record CategoryResponseDTO(
        Long id,
        String name) {
}
