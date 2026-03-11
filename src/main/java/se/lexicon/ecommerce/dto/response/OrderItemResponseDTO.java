package se.lexicon.ecommerce.dto.response;

import java.math.BigDecimal;

/**
 * Response DTO used to expose item data for an order.
 */
public record OrderItemResponseDTO(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        String appliedPromoCode,
        BigDecimal discountAmount) {
}
