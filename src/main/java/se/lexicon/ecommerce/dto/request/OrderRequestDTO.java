package se.lexicon.ecommerce.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request DTO for placing an {@link se.lexicon.ecommerce.model.Order},
 * mapped later to {@link se.lexicon.ecommerce.dto.response.OrderResponseDTO}.
 *
 * @param customerId customer placing the order
 * @param orderItems requested order items
 */
public record OrderRequestDTO(
        @NotNull @Positive Long customerId,
        @NotEmpty List<@Valid OrderItemRequest> orderItems) {

    /**
         * Nested request DTO for one order line item.
         *
         * @param productId product id to purchase
         * @param quantity requested quantity
     */
    public record OrderItemRequest(
            @NotNull @Positive Long productId,
            @NotNull @Min(value = 1) Integer quantity) {
    }
}
