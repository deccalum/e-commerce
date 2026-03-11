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
 * Default {@link OrderService} implementation for
 * {@link Order} workflows.
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
     * Builds an {@link OrderItem} from an item request and parent order.
     *
     * @param itemReq source {@link OrderItemRequest}
     * @param order parent {@link Order}
     * @return constructed order item
     * @throws IllegalArgumentException if product is not found
     */
    private OrderItem buildOrderItem(OrderItemRequest itemReq, Order order) {
        Product product = productRepository.findById(itemReq.productId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found: " + itemReq.productId()));

        return OrderItem.builder()
                .product(product)
                .quantity(itemReq.quantity())
                .priceAtPurchase(product.getPrice())
                .order(order)
                .build();
    }

    /**
     * Resolves text status to {@link OrderStatus}.
     *
     * @param status textual status
     * @return matching enum value
     * @throws IllegalArgumentException if status is invalid
     */
    private OrderStatus resolveStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }

    /**
        * Places a new order and returns its mapped response.
        *
        * @param request input {@link OrderRequestDTO}
        * @return placed order as {@link OrderResponseDTO}
        * @throws IllegalArgumentException if request is invalid or references missing entities
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
        * Finds an order by id.
        *
        * @param id order id
        * @return matching {@link OrderResponseDTO}
        * @throws IllegalArgumentException if order is not found
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
        *
        * @param id order id
        * @param status target status text
        * @return updated {@link OrderResponseDTO}
        * @throws IllegalArgumentException if order is not found or status is invalid
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
        * Cancels an existing order by setting status to CANCELLED.
        *
        * @param id order id
        * @throws IllegalArgumentException if order is not found
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