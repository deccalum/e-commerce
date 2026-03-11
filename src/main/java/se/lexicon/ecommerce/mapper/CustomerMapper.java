package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.request.CustomerRequestDTO;
import se.lexicon.ecommerce.dto.response.CustomerResponseDTO;
import se.lexicon.ecommerce.model.Address;
import se.lexicon.ecommerce.model.Customer;

/**
 * Mapper interface for converting between Customer entities and their
 * corresponding DTOs.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "fullName", expression = "java(customer.getFirstName() + \" \" + customer.getLastName())")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(source = "address.street", target = "addressResponse.street")
    @Mapping(source = "address.city", target = "addressResponse.city")
    @Mapping(source = "address.zipCode", target = "addressResponse.zipCode")
    CustomerResponseDTO toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "zipCode", target = "address.zipCode")
    Customer toEntity(CustomerRequestDTO request);

    // MapStruct will generate this mapping for nested Address -> AddressResponse
    CustomerResponseDTO.AddressResponse addressToAddressResponse(Address address);
}