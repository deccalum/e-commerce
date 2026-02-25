package se.lexicon.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.OrderItem;

/**
 * Repository for {@link OrderItem} persistence and optional order-item queries.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Finds order items by order id.
     *
     * @param orderId order id
     * @return matching order items
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * Finds order items by product id.
     *
     * @param productId product id
     * @return matching order items
     */
    List<OrderItem> findByProductId(Long productId);

    /**
     * Finds order items with quantity greater than the provided value.
     *
     * @param quantity quantity threshold
     * @return matching order items
     */
    List<OrderItem> findByQuantityGreaterThan(int quantity);
}
