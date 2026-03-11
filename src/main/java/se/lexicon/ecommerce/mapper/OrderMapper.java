package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.request.OrderRequestDTO;
import se.lexicon.ecommerce.dto.response.OrderItemResponseDTO;
import se.lexicon.ecommerce.dto.response.OrderResponseDTO;
import se.lexicon.ecommerce.model.Order;
import se.lexicon.ecommerce.model.OrderItem;

/**
 * Mapper for converting between {@link Order},
 * {@link OrderRequestDTO}, and {@link OrderResponseDTO}.
 * Uses MapStruct for implementation generation.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Maps an {@link Order} entity to an {@link OrderResponseDTO}.
     *
     * @param order source entity
     * @return mapped response DTO
     */
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "customerName", expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "addressResponse", expression = "java(order.getCustomer().getAddress().getStreet() + \", \" + order.getCustomer().getAddress().getCity() + \", \" + order.getCustomer().getAddress().getZipCode())")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderResponseDTO toResponse(Order order);

    /**
     * Maps an {@link OrderRequestDTO} to an {@link Order} entity.
     *
     * @param request source request DTO
     * @return mapped order entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItems", source = "orderItems")
    Order toEntity(OrderRequestDTO request);

    /**
     * Maps an {@link OrderItem} entity to an {@link OrderItemResponseDTO}.
     *
     * @param item source order item
     * @return mapped order item response DTO
     */
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "priceAtPurchase")
    // @Mapping(target = "appliedPromoCode", source = "appliedPromoCode")
    @Mapping(target = "appliedPromoCode", ignore = true) 
    // @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "discountAmount", ignore = true)
    OrderItemResponseDTO toResponse(OrderItem item);

    /**
     * Maps an {@link OrderRequestDTO.OrderItemRequest} to an {@link OrderItem}.
     *
     * @param item source nested request DTO
     * @return mapped order item entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "priceAtPurchase", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderRequestDTO.OrderItemRequest item);
}