package se.lexicon.ecommerce.repository;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, BigInteger> {

    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(BigInteger productId);
    List<OrderItem> findByQuantityGreaterThan(int quantity);
}
