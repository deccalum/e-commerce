package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.request.CustomerRequestDTO;
import se.lexicon.ecommerce.dto.response.CustomerResponseDTO;
import se.lexicon.ecommerce.model.Address;
import se.lexicon.ecommerce.model.Customer;

/**
 * Mapper for converting between {@link Customer},
 * {@link CustomerRequestDTO}, and {@link CustomerResponseDTO}.
 * Uses MapStruct for implementation generation.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Maps a {@link Customer} entity to a {@link CustomerResponseDTO}.
     *
     * @param customer source entity
     * @return mapped response DTO
     */
    @Mapping(target = "fullName", expression = "java(customer.getFirstName() + \" \" + customer.getLastName())")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(source = "address.street", target = "addressResponse.street")
    @Mapping(source = "address.city", target = "addressResponse.city")
    @Mapping(source = "address.zipCode", target = "addressResponse.zipCode")
    CustomerResponseDTO toResponse(Customer customer);

    /**
     * Maps a {@link CustomerRequestDTO} to a {@link Customer} entity.
     *
     * @param request source request DTO
     * @return mapped customer entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "zipCode", target = "address.zipCode")
    Customer toEntity(CustomerRequestDTO request);

    /**
     * Maps nested {@link Address} to {@link CustomerResponseDTO.AddressResponse}.
     *
     * @param address source address
     * @return mapped nested response object
     */
    CustomerResponseDTO.AddressResponse addressToAddressResponse(Address address);
}