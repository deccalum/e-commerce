package se.lexicon.ecommerce.dto.response;

import java.util.List;

import se.lexicon.ecommerce.model.OrderStatus;

/**
 * Response DTO for exposing {@link se.lexicon.ecommerce.model.Order} data.
 *
 * @param orderId order id
 * @param customerName customer display name
 * @param orderStatus order lifecycle status
 * @param addressResponse flattened customer address
 * @param orderItems mapped item responses
 */
public record OrderResponseDTO(
        Long orderId,
        String customerName,
        OrderStatus orderStatus,
        String addressResponse,
        List<OrderItemResponseDTO> orderItems) {
}

