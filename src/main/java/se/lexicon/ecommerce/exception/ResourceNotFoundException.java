package se.lexicon.ecommerce.exception;

/**
 * Thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Creates a new resource-not-found exception.
     *
     * @param message error details
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}