package se.lexicon.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import se.lexicon.ecommerce.dto.request.ProductRequestDTO;
import se.lexicon.ecommerce.dto.response.ProductResponseDTO;
import se.lexicon.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO response = productService.create(productRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> response = productService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductResponseDTO> searchByName(@RequestParam String name) {
        ProductResponseDTO response = productService.searchByName(name);

        return ResponseEntity.ok(response);
    }
}
