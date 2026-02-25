package se.lexicon.ecommerce.model;

/**
 * Represents the lifecycle status of an order.
 */
public enum OrderStatus {
    /** Order is created but not yet paid. */
    CREATED,
    /** Order has been paid. */
    PAID,
    /** Order has been shipped. */
    SHIPPED,
    /** Order has been cancelled. */
    CANCELLED
}
