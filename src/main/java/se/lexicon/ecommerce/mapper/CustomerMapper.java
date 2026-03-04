package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.customer.CustomerRequest;
import se.lexicon.ecommerce.dto.customer.CustomerResponse;
import se.lexicon.ecommerce.model.Customer;

/**
 * Mapper interface for converting between Customer entities and their corresponding DTOs.
 * Utilizes MapStruct for automatic implementation generation.
 */

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "fullName", expression = "java(customer.getFirstName() + \" \" + customer.getLastName())")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(source = "address.street", target = "addressResponse.street")
    @Mapping(source = "address.city", target = "addressResponse.city")
    @Mapping(source = "address.zipCode", target = "addressResponse.zipCode")
    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "zipCode", target = "address.zipCode")
    Customer toEntity(CustomerRequest request);
}