package se.lexicon.ecommerce.dto.order;

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
}

