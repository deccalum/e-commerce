package se.lexicon.ecommerce.dto.response;

import java.math.BigDecimal;

/**
 * Response DTO used to expose {@link Product}
 * data to API clients. Usually produced from a
 * {@link ProductRequestDTO} via mapping.
 */
public record ProductResponseDTO(
        Long id,
        String name,
        String imageUrl,
        BigDecimal price,
        String categoryName) {
}
