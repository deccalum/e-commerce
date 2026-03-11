package se.lexicon.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;
import se.lexicon.ecommerce.model.Product;

/**
 * Mapper for converting between {@link Product}, {@link ProductRequestDTO},
 * and {@link ProductResponseDTO}.
 * Uses MapStruct for implementation generation.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Maps a {@link Product} entity to a {@link ProductResponseDTO}.
     *
     * @param product source entity
     * @return mapped response DTO
     */
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDTO toResponse(Product product);

    /**
     * Maps a {@link ProductRequestDTO} to a {@link Product} entity.
     *
     * @param request source request DTO
     * @return mapped product entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductRequestDTO request);
}