package se.lexicon.ecommerce.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.ecommerce.dto.order.OrderRequest;
import se.lexicon.ecommerce.dto.order.OrderRequest.OrderItemRequest;
import se.lexicon.ecommerce.dto.order.OrderResponse;
import se.lexicon.ecommerce.mapper.OrderMapper;
import se.lexicon.ecommerce.model.OrderItem;
import se.lexicon.ecommerce.model.OrderStatus;
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

	public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository,
			ProductRepository productRepository, PromotionRepository promotionRepository, OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.promotionRepository = promotionRepository;
		this.orderMapper = orderMapper;
	}

	@Transactional
	@Override
	public OrderResponse placeOrder(OrderRequest request) {
		return Optional.ofNullable(request)
				.map(orderMapper::toEntity)
				.map(order -> {
					order.setCustomer(customerRepository.findById(request.customerId())
							.orElseThrow(
									() -> new IllegalArgumentException("Customer not found: " + request.customerId())));
					order.setOrderDate(Instant.now());
					order.setStatus(OrderStatus.CREATED);
					order.setOrderItems(request.orderItems().stream()
							.<OrderItem>map(
									(OrderItemRequest itemReq) -> productRepository.findById(itemReq.productId())
											.map(product -> {
												OrderItem orderItem = new OrderItem();
												orderItem.setProduct(product);
												orderItem.setQuantity(itemReq.quantity());
												orderItem.setPriceAtPurchase(product.getPrice());
												orderItem.setOrder(order);
												return orderItem;
											})
											.orElseThrow(() -> new IllegalArgumentException(
													"Product not found: " + itemReq.productId())))
							.toList());
					return order;
				})
				.map(orderRepository::save)
				.map(orderMapper::toResponse)
				.orElseThrow(() -> new IllegalArgumentException("Invalid order request"));
	}
}