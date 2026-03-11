package se.lexicon.ecommerce.dto.response;

import java.math.BigDecimal;

/**
 * Response DTO used to expose product data to API clients.
 */
public record ProductResponseDTO(
        Long id,
        String name,
        String imageUrl,
        BigDecimal price,
        String categoryName) {
}
