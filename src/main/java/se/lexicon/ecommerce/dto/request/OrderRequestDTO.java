package se.lexicon.ecommerce.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request DTO used to place an order.
 */
public record OrderRequestDTO(
        @NotNull @Positive Long customerId,
        @NotEmpty List<@Valid OrderItemRequest> orderItems) {
    /**
     * Request DTO used to describe each item in an order.
     */
    public record OrderItemRequest(
            @NotNull @Positive Long productId,
            @NotNull @Min(value = 1) Integer quantity) {
    }
}
