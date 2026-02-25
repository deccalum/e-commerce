package se.lexicon.ecommerce.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a customer purchase order with status and line items.
 */
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    /**
     * Enforces the business rule that an order must contain at least one item.
     */
    @PrePersist
    @PreUpdate
    private void validateOrderItems() {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalStateException("Order must contain at least one order item.");
        }
    }

}