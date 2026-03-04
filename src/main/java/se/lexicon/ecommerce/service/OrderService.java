package se.lexicon.ecommerce.service;

import jakarta.transaction.Transactional;
import se.lexicon.ecommerce.dto.order.OrderRequest;
import se.lexicon.ecommerce.dto.order.OrderResponse;

public interface OrderService {
    @Transactional
    public OrderResponse placeOrder(OrderRequest request);
}