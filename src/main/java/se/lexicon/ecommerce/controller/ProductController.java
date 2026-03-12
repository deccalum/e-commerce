package se.lexicon.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;
import se.lexicon.ecommerce.service.ProductService;

/**
 * REST controller for {@link se.lexicon.ecommerce.model.Product} endpoints.
 * Uses {@link ProductRequestDTO} for input and {@link ProductResponseDTO}
 * for output.
 * 
 * @Validated enables method parameter validation for constraints on
 *             {@code @RequestParam} and {@code @PathVariable} values.
 * @RequestParam binds query parameters like `?name=foo`.
 * @Valid triggers Jakarta Bean Validation on the bound DTO.
 * @RequestBody binds the incoming JSON to the DTO and applies validation.
 * 
 */
@RestController
@Validated
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a product.
     *
     * @param productRequestDTO input {@link ProductRequestDTO}
     * @return created {@link ProductResponseDTO} and HTTP 201
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO response = productService.create(productRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Lists all products.
     *
     * @return list of {@link ProductResponseDTO} and HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> response = productService.findAll();

        return ResponseEntity.ok(response);
    }

    /**
     * Searches products by name text.
     *
     * @param name name or partial name
     * @return matching {@link ProductResponseDTO} and HTTP 200
     */
    @GetMapping("/search")
    public ResponseEntity<ProductResponseDTO> searchByName(
            @RequestParam @NotBlank @Size(max = 150) String name) {
        ProductResponseDTO response = productService.searchByName(name);

        return ResponseEntity.ok(response);
    }
}
