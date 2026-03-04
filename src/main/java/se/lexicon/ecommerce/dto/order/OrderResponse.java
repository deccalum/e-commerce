package se.lexicon.ecommerce.dto.order;

import java.math.BigDecimal;
import java.util.List;

import se.lexicon.ecommerce.model.OrderStatus;

/**
 * Response DTO used to expose order data to API clients.
 */
public record OrderResponse(
                Long orderId,
                String customerName,
                OrderStatus orderStatus,
                String addressResponse,
                List<OrderItemResponse> orderItems) {
        /**
         * Response DTO used to expose item data for an order.
         */
        public record OrderItemResponse(
                        Long productId,
                        String productName,
                        BigDecimal price,
                        Integer quantity) {
        }
}