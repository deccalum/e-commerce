package se.lexicon.ecommerce.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Order;
import se.lexicon.ecommerce.model.OrderStatus;

/**
 * Repository for {@link Order} persistence and reporting-oriented order
 * queries.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds orders by status while eagerly loading order items to avoid N+1 issues.
     *
     * @param status target order status
     * @return matching orders with items loaded
     */
    @EntityGraph(attributePaths = "orderItems")
    List<Order> findByStatus(OrderStatus status);

    /**
     * Finds all orders for a customer id.
     *
     * @param customerId customer id
     * @return matching orders
     */
    List<Order> findByCustomerId(Long customerId);

    /**
     * Finds orders by customer id and status.
     *
     * @param customerId customer id
     * @param status     order status
     * @return matching orders
     */
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    /**
     * Finds orders created after a given instant.
     *
     * @param date lower bound instant
     * @return matching orders
     */
    List<Order> findByOrderDateAfter(Instant date);

    /**
     * Finds orders created between two instants.
     *
     * @param startDate inclusive lower bound
     * @param endDate   inclusive upper bound
     * @return matching orders
     */
    List<Order> findByOrderDateBetween(Instant startDate, Instant endDate);

    /**
     * Finds orders that contain a specific product id.
     *
     * @param productId product id
     * @return matching orders
     */
    List<Order> findByOrderItemsProductId(Long productId);

    /**
     * Counts orders by status.
     *
     * @param status order status
     * @return number of matching orders
     */
    long countByStatus(OrderStatus status);
}