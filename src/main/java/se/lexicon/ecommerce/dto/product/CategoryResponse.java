package se.lexicon.ecommerce.dto.product;

/**
 * Response DTO used to expose category data to API clients.
 */
public record CategoryResponse(
        Long id,
        String name) {
}
