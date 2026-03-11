package se.lexicon.ecommerce.dto.response;

/**
 * Response DTO used to expose category data to API clients.
 */
public record CategoryResponseDTO(
        Long id,
        String name) {
}
