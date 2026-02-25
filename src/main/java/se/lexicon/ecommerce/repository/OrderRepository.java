package se.lexicon.ecommerce.repository;

import java.time.Instant;
import java.util.List;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.Order;
import se.lexicon.ecommerce.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "orderItems")
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
    List<Order> findByOrderDateAfter(Instant date);
    List<Order> findByOrderDateBetween(Instant startDate, Instant endDate);
    List<Order> findByOrderItemsProductId(BigInteger productId);
    long countByStatus(OrderStatus status);
}