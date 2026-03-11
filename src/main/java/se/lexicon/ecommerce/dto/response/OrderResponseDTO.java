package se.lexicon.ecommerce.dto.response;

import java.util.List;

import se.lexicon.ecommerce.model.OrderStatus;

/**
 * Response DTO used to expose order data to API clients.
 */
public record OrderResponseDTO(
        Long orderId,
        String customerName,
        OrderStatus orderStatus,
        String addressResponse,
        List<OrderItemResponseDTO> orderItems) {
}

