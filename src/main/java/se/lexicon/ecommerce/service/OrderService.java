package se.lexicon.ecommerce.service;

import jakarta.transaction.Transactional;
import se.lexicon.ecommerce.dto.request.OrderRequestDTO;
import se.lexicon.ecommerce.dto.response.OrderResponseDTO;

public interface OrderService {
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request);

    public OrderResponseDTO findById(Long id);

    public OrderResponseDTO updateOrderStatus(Long id, String status);
    
    public void cancelOrder(Long id);

}