package se.lexicon.ecommerce.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating or updating {@link se.lexicon.ecommerce.model.Product}
 * data and later mapping to
 * {@link se.lexicon.ecommerce.dto.response.ProductResponseDTO}.
 *
 * @param name product name
 * @param price product price
 * @param categoryId related category id
 */
public record ProductRequestDTO(
        @NotBlank @Size(max = 150) String name,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) @DecimalMin("0.0") BigDecimal price,
        @NotNull @Positive Long categoryId) {
}
