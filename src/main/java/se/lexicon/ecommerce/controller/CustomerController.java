package se.lexicon.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import se.lexicon.ecommerce.dto.request.CustomerRequestDTO;
import se.lexicon.ecommerce.dto.response.CustomerResponseDTO;
import se.lexicon.ecommerce.service.CustomerService;

/**
 * Controller for managing customers.
 * Provides endpoints for creating, retrieving, and updating customers.
 * 
 * @Valid triggers Jakarta Bean Validation on the bound DTO.
 * @RequestBody binds the incoming JSON to the DTO and applies validation.
 * @PathVariable binds path values like `{id}`.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a customer.
     *
     * @param customerRequestDTO customer input payload
     * @return created customer and HTTP 201
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> register(
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO response = customerService.register(customerRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves a customer by id.
     *
     * @param id customer id
     * @return customer data and HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        CustomerResponseDTO response = customerService.findById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing customer.
     *
     * @param id customer id
     * @param customerRequestDTO updated customer payload
     * @return updated customer and HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO response = customerService.update(id, customerRequestDTO);

        return ResponseEntity.ok(response);
    }
}
