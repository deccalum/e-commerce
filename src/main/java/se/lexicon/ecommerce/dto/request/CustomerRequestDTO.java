package se.lexicon.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating or updating {@link se.lexicon.ecommerce.model.Customer}
 * data, typically mapped to {@link se.lexicon.ecommerce.dto.response.CustomerResponseDTO}.
 *
 * Password is validated for basic strength and must be hashed in the service
 * layer before persistence.
 *
 * @param firstName customer first name
 * @param lastName customer last name
 * @param email customer email
 * @param password raw password before hashing
 * @param street customer street
 * @param city customer city
 * @param zipCode customer zip/postal code
 */

public record CustomerRequestDTO(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Size(max = 150) @Email String email,
        @NotBlank @Size(min = 8, max = 72) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).+$") String password,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String zipCode) {
}