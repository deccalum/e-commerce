package se.lexicon.ecommerce.dto.product;

import java.math.BigDecimal;

/**
 * Response DTO used to expose product data to API clients.
 */
public record ProductResponse(
        Long id,
        String name,
        String imageUrl,
        BigDecimal price,
        String categoryName) {
}
