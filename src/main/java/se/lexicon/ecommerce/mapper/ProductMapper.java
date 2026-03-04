package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.product.ProductRequest;
import se.lexicon.ecommerce.dto.product.ProductResponse;
import se.lexicon.ecommerce.model.Product;

/**
 * Mapper interface for converting between Product entities and their
 * corresponding DTOs.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductRequest request);
}

/*
 ** 
 * How it works**
 * - MapStruct generates mapper implementations at compile time from your
 * interface signatures and `@Mapping` rules.
 * - `toResponse` is for entity -> API DTO (flattening nested values like
 * `category.name -> categoryName`).
 * - `toEntity` is for request DTO -> entity skeleton (only input fields);
 * service fills business-managed fields and repository-managed relations.
 * - In your project, mapper should convert shape; service should handle
 * lookups/transactions/business logic.
 *
 */