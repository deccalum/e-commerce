package se.lexicon.ecommerce.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO used to create or update customer data.
 *
 * Password is validated for basic strength and must be hashed in the service
 * layer before persistence.
 */
public record CustomerRequest(
                @NotBlank @Size(max = 100) String firstName,
                @NotBlank @Size(max = 100) String lastName,
                @NotBlank @Size(max = 150) @Email String email,
                @NotBlank @Size(min = 8, max = 72) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).+$") String password,
                @NotBlank String street,
                @NotBlank String city,
                @NotBlank String zipCode) {
}