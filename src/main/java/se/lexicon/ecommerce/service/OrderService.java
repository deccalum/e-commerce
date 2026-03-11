package se.lexicon.ecommerce.service;

import jakarta.transaction.Transactional;
import se.lexicon.ecommerce.dto.request.OrderRequestDTO;
import se.lexicon.ecommerce.dto.response.OrderResponseDTO;

/**
 * Service contract for order workflows.
 */
public interface OrderService {
    /**
     * Places a new order.
     *
     * @param request order input
     * @return created order
     */
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request);

    /**
     * Finds an order by id.
     *
     * @param id order id
     * @return matching order
     */
    public OrderResponseDTO findById(Long id);

    /**
     * Updates order status.
     *
     * @param id order id
     * @param status target status
     * @return updated order
     */
    public OrderResponseDTO updateOrderStatus(Long id, String status);

    /**
     * Cancels an order.
     *
     * @param id order id
     */
    public void cancelOrder(Long id);

}