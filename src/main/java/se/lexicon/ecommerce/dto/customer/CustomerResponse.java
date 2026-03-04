package se.lexicon.ecommerce.dto.customer;

/**
 * Response DTO used to expose customer data to API clients.
 */
public record CustomerResponse(
                Long id,
                String fullName,
                String email,
                String addressResponse) {
}