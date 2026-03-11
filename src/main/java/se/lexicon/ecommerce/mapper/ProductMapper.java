package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;
import se.lexicon.ecommerce.model.Product;

/**
 * Mapper interface for converting between Product entities and their
 * corresponding DTOs.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductRequestDTO request);
}