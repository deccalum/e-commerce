package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.order.OrderRequest;
import se.lexicon.ecommerce.dto.order.OrderResponse;
import se.lexicon.ecommerce.model.Order;
import se.lexicon.ecommerce.model.OrderItem;

/**
 * Mapper interface for converting between Order entities and their
 * corresponding DTOs.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "customerName", expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "addressResponse", expression = "java(order.getCustomer().getAddress().getStreet() + \", \" + order.getCustomer().getAddress().getCity() + \", \" + order.getCustomer().getAddress().getZipCode())")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderResponse toResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItems", source = "orderItems")
    Order toEntity(OrderRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "priceAtPurchase")
    OrderResponse.OrderItemResponse toResponse(OrderItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "priceAtPurchase", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderRequest.OrderItemRequest item);
}