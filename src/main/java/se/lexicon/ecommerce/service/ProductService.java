package se.lexicon.ecommerce.service;

import java.util.List;

import se.lexicon.ecommerce.model.Product;
import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;

/**
 * Service contract for product workflows based on
 * {@link Product}.
 */
public interface ProductService {
    /**
     * Creates a product.
     *
     * @param request input {@link ProductRequestDTO}
     * @return created {@link ProductResponseDTO}
     */
    public ProductResponseDTO create(ProductRequestDTO request);

    /**
     * Lists all products.
     *
        * @return all products as {@link ProductResponseDTO} entries
     */
    public List<ProductResponseDTO> findAll();

    /**
     * Searches for a product by name text.
     *
     * @param name name or partial name
     * @return first matching {@link ProductResponseDTO}
     */
    public ProductResponseDTO searchByName(String name);
}