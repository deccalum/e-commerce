package se.lexicon.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.ecommerce.dto.request.OrderRequestDTO;
import se.lexicon.ecommerce.dto.request.OrderRequestDTO.OrderItemRequest;
import se.lexicon.ecommerce.dto.response.OrderResponseDTO;
import se.lexicon.ecommerce.mapper.OrderMapper;
import se.lexicon.ecommerce.model.Order;
import se.lexicon.ecommerce.model.OrderItem;
import se.lexicon.ecommerce.model.OrderStatus;
import se.lexicon.ecommerce.model.Product;
import se.lexicon.ecommerce.repository.CustomerRepository;
import se.lexicon.ecommerce.repository.OrderRepository;
import se.lexicon.ecommerce.repository.ProductRepository;
import se.lexicon.ecommerce.repository.PromotionRepository;

/**
 * Service implementation for managing orders.
 * Orchestrates order placement, including customer and product validation,
 * price capture, and discount application.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            PromotionRepository promotionRepository,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.orderMapper = orderMapper;
    }
    /**
     * Helper method to build an OrderItem entity from an OrderItemRequest DTO.
     * @param itemReq the order item request DTO
     * @param order the parent order entity
     * @return the constructed OrderItem entity
     * @throws IllegalArgumentException if the product is not found
     */
    private OrderItem buildOrderItem(OrderItemRequest itemReq, Order order) {
        Product product = productRepository.findById(itemReq.productId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found: " + itemReq.productId()));
                        // also check if quantity is valid
        return OrderItem.builder()
                .product(product)
                .quantity(itemReq.quantity())
                .priceAtPurchase(product.getPrice())
                .order(order)
                .build();
    }

    /**
     * Helper method to resolve a string status to an OrderStatus enum.
     * @param status the string representation of the order status
     * @return the corresponding OrderStatus enum value
     * @throws IllegalArgumentException if the status is invalid
     */
    private OrderStatus resolveStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }

    
    //--- Public service methods ---//
    /**
     * Places a new order based on the provided order request DTO.
     * Validates the request, captures product prices, and saves the order.
     * @Transactional to ensure atomicity of the order placement process.
     * @param request the order request DTO containing customer ID and order items
     * @return the response DTO representing the placed order
     * @throws IllegalArgumentException if the request is invalid or any entity is not found
     */
    @Transactional
    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {
        return Optional.ofNullable(request)
                .map(orderMapper::toEntity)
                .map(order -> {
                    order.setCustomer(customerRepository.findById(request.customerId()).orElseThrow(
                            () -> new IllegalArgumentException("Customer not found: " + request.customerId())));
                    order.setOrderItems(request.orderItems().stream()
                            .map(itemReq -> buildOrderItem(itemReq, order))
                            .toList());
                    return order;
                })
                .map(orderRepository::save)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order request"));
    }

    /**
     * Finds an order by its ID.
     * @param id the ID of the order
     * @return the response DTO representing the found order
     * @throws IllegalArgumentException if the order is not found
     */
    @Override
    public OrderResponseDTO findById(Long id) {
        return Optional.ofNullable(id)
                .filter(value -> value > 0)
                .flatMap(orderRepository::findById)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    /**
     * Updates the status of an existing order.
     * @param id the ID of the order to update
     * @param status the new status to set
     * @return the response DTO representing the updated order
     * @throws IllegalArgumentException if the order is not found or the status is invalid
     */
    @Override
    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(resolveStatus(status));
                    return order;
                })
                .map(orderRepository::save)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    /**
     * Cancels an existing order by setting its status to CANCELLED.
     * @param id the ID of the order to cancel
     * @throws IllegalArgumentException if the order is not found
     */
    @Override
    public void cancelOrder(Long id) {
        orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(OrderStatus.CANCELLED);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }
}