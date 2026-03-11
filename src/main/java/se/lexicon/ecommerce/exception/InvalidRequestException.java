package se.lexicon.ecommerce.exception;

/**
 * Thrown when an incoming request is syntactically or semantically invalid.
 */
public class InvalidRequestException extends RuntimeException {
    /**
     * Creates a new invalid request exception.
     *
     * @param message error details
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}