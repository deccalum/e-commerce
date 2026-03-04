package se.lexicon.ecommerce.service;

import org.springframework.stereotype.Service;

import se.lexicon.ecommerce.dto.order.OrderRequest;
import se.lexicon.ecommerce.dto.order.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeOrder'");
    }
}
