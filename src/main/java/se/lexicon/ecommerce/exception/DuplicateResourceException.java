package se.lexicon.ecommerce.exception;

/**
 * Thrown when creating a resource that already exists.
 */
public class DuplicateResourceException extends RuntimeException {
    /**
     * Creates a new duplicate resource exception.
     *
     * @param message error details
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}