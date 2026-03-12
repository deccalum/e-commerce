# PART 4

## Submission Checklist

- [x] **Git Branch**: Create a feature branch for Part 4 (e.g., `feature/rest-api`).
- [x] **Controllers**: Implement the required REST controllers with appropriate Spring annotations.
- [x] **Endpoints**: Create the required REST endpoints for CRUD operations and searching.
- [x] **Exception Handling**: Implement a [GlobalExceptionHandler](src/main/java/se/lexicon/ecommerce/exception/GlobalExceptionHandler.java) for consistent error responses.
- [x] **Validation**: Ensure that all incoming requests are properly validated.
      _Verified in [ControllerValidationTest](src/test/java/se/lexicon/ecommerce/controller/ControllerValidationTest.java) with standalone MockMvc tests._
- [x] **Verification**: Use a REST client (like Postman or curl) to test all API endpoints and verify the correct HTTP status codes.
      _Used [VS Code REST Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) with [request.http](request.http) file._
- [x] **Swagger UI**: Verify that the API documentation is accessible at `/swagger-ui.html`.
- [x] **Commits**: Make descriptive commits for each major step.
- [x] **Push**: Push the branch to GitHub and provide the link.

## REST Guide

### Implemented Controllers

- [CustomerController](src/main/java/se/lexicon/ecommerce/controller/CustomerController.java)
- [ProductController](src/main/java/se/lexicon/ecommerce/controller/ProductController.java)
- [CategoryController](src/main/java/se/lexicon/ecommerce/controller/CategoryController.java)
- [OrderController](src/main/java/se/lexicon/ecommerce/controller/OrderController.java)

### Endpoint Summary

| Resource | Method | Path                               | Purpose                | Expected Status |
| -------- | ------ | ---------------------------------- | ---------------------- | --------------- |
| Customer | POST   | `/api/v1/customers`                | Create customer        | `201 Created`   |
| Customer | GET    | `/api/v1/customers/{id}`           | Get customer by id     | `200 OK`        |
| Customer | PUT    | `/api/v1/customers/{id}`           | Update customer        | `200 OK`        |
| Product  | POST   | `/api/v1/products`                 | Create product         | `201 Created`   |
| Product  | GET    | `/api/v1/products`                 | List products          | `200 OK`        |
| Product  | GET    | `/api/v1/products/search?name=...` | Search product by name | `200 OK`        |
| Category | POST   | `/api/v1/categories`               | Create category        | `201 Created`   |
| Category | GET    | `/api/v1/categories`               | List categories        | `200 OK`        |
| Order    | POST   | `/api/v1/orders`                   | Place order            | `201 Created`   |

### Core Spring REST Annotations

- `@RestController`: marks the class as a REST endpoint provider and serializes return values as JSON.
- `@RequestMapping("/base")`: sets the base path for all methods in that controller.
- `@PostMapping`, `@GetMapping`, `@PutMapping`: map HTTP method + path to handler methods.
- `@RequestBody`: binds JSON request body to a DTO.
- `@Valid`: triggers Jakarta Bean Validation on the bound DTO.
- `@Validated`: enables method parameter validation on controller methods (for example `@RequestParam`, `@PathVariable`).
- `@PathVariable`: binds path values like `{id}`.
- `@RequestParam`: binds query string values like `?name=keyboard`.
- `@NotBlank`, `@Size`, `@Positive`, `@Email`, `@Pattern`: common constraint annotations used with `@Valid` or `@Validated`.

### Validation Quick Guide

Use `@Valid` when validating request body DTOs:

```java
@PostMapping
public ResponseEntity<ResourceResponseDTO> create(
        @Valid @RequestBody ResourceRequestDTO request) {
    ...
}
```

Use `@Validated` on the controller class when validating method parameters:

```java
@Validated
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/search")
    public ResponseEntity<ProductResponseDTO> searchByName(
            @RequestParam @NotBlank @Size(max = 150) String name) {
        ...
    }
}
```

Difference summary:

1. `@Valid` validates object graphs (typically DTOs in `@RequestBody`).
2. `@Validated` activates validation for method parameters like query params and path variables.
3. Constraint annotations (`@NotBlank`, `@Size`, and others) define the actual validation rules.

### Controller Pattern Used

```java
@PostMapping
public ResponseEntity<ResourceResponseDTO> create(
        @Valid @RequestBody ResourceRequestDTO request) {
    ResourceResponseDTO response = service.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

```java
@GetMapping("/{id}")
public ResponseEntity<ResourceResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
}
```

```java
@GetMapping("/search")
public ResponseEntity<ResourceResponseDTO> searchByName(@RequestParam String name) {
    return ResponseEntity.ok(service.searchByName(name));
}
```

```java
@Validated
@GetMapping("/search")
public ResponseEntity<ResourceResponseDTO> searchByName(
        @RequestParam @NotBlank @Size(max = 150) String name) {
    return ResponseEntity.ok(service.searchByName(name));
}
```

### Validation and Error Handling Flow

1. `@Valid` validates request DTO fields.
2. Service throws domain exceptions (`InvalidRequestException`, `ResourceNotFoundException`, `DuplicateResourceException`, `BusinessRuleException`).
3. [GlobalExceptionHandler](src/main/java/se/lexicon/ecommerce/exception/GlobalExceptionHandler.java) converts them into consistent HTTP responses.
4. `MethodArgumentNotValidException` is handled for validation failures (HTTP `400`).
5. Method-parameter validation errors are handled via `HandlerMethodValidationException` / `ConstraintViolationException` (HTTP `400`).
