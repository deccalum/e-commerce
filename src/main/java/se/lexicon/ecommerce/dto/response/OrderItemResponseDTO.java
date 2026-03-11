package se.lexicon.ecommerce.dto.response;

import java.math.BigDecimal;

/**
 * Response DTO for exposing {@link se.lexicon.ecommerce.model.OrderItem} data.
 *
 * @param productId related product id
 * @param productName product display name
 * @param price captured item price
 * @param quantity ordered quantity
 * @param appliedPromoCode applied promotion code, if any
 * @param discountAmount discount amount, if any
 */
public record OrderItemResponseDTO(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        String appliedPromoCode,
        BigDecimal discountAmount) {
}
