package se.lexicon.ecommerce.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request DTO used to create or update product data.
 */
public record ProductRequest(
        @NotBlank @Size(max = 150) String name,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) @DecimalMin("0.0") BigDecimal price,
        @NotNull @Positive Long categoryId) {
}
