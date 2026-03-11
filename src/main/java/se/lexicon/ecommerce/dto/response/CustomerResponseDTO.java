package se.lexicon.ecommerce.dto.response;

/**
 * Response DTO for exposing {@link se.lexicon.ecommerce.model.Customer} data.
 *
 * @param id customer id
 * @param fullName customer full name
 * @param email customer email
 * @param addressResponse mapped address data
 */
public record CustomerResponseDTO(
        Long id,
        String fullName,
        String email,
        AddressResponse addressResponse) {

    /**
     * Nested response DTO for {@link se.lexicon.ecommerce.model.Address} data.
     *
     * @param street street name
     * @param city city name
     * @param zipCode zip/postal code
     */
    public record AddressResponse(
            String street,
            String city,
            String zipCode) {
    }
}