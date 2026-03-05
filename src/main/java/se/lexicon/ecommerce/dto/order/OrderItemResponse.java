package se.lexicon.ecommerce.dto.order;

import java.math.BigDecimal;

/**
 * Response DTO used to expose item data for an order.
 */
public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        String appliedPromoCode,
        BigDecimal discountAmount) {
}
