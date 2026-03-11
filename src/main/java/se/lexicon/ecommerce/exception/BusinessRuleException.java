package se.lexicon.ecommerce.exception;

/**
 * Thrown when a business rule is violated.
 */
public class BusinessRuleException extends RuntimeException {
    /**
     * Creates a new business rule exception.
     *
     * @param message error details
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}