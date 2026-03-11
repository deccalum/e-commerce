# Customer Controller Notes

This section documents the behavior of `CustomerController` in a clear format.

## Class-Level Annotations

- `@RestController`
  - Tells Spring this class handles REST requests.
  - Return values are serialized (typically JSON) and written to the HTTP response body.

- `@RequestMapping("/api/v1/customers")`
  - Sets the base path for all endpoints in this controller.
  - Every method-level mapping is appended to this path.

## Dependency

- `private final CustomerService customerService;`
  - The controller delegates business logic to the service layer.
  - The dependency is provided through constructor injection.

## Endpoints

### 1. Create Customer

- Annotation: `@PostMapping`
- Final path: `POST /api/v1/customers`
- Method parameter: `@Valid @RequestBody CustomerRequestDTO customerRequestDTO`
  - `@RequestBody` maps incoming JSON to `CustomerRequestDTO`.
  - `@Valid` triggers Jakarta Bean Validation on the DTO.
- Response:
  - Uses `ResponseEntity.status(HttpStatus.CREATED).body(response)`.
  - Returns HTTP `201 Created` with the created customer data.

What `@PostMapping` does exactly:

- It maps this method to HTTP POST requests.
- Without a path value, it maps to the class base path.
- Here, that means `POST /api/v1/customers`.
- It is shorthand for `@RequestMapping(method = RequestMethod.POST, value = ...)`.

### 2. Get Customer By ID

- Annotation: `@GetMapping("/{id}")`
- Final path: `GET /api/v1/customers/{id}`
- Method parameter: `@PathVariable Long id`
  - Extracts the `id` segment from the URL.
- Response:
  - Uses `ResponseEntity.ok(response)`.
  - Returns HTTP `200 OK` with customer data.

### 3. Update Customer

- Annotation: `@PutMapping("/{id}")`
- Final path: `PUT /api/v1/customers/{id}`
- Method parameters:
  - `@PathVariable Long id`
  - `@Valid @RequestBody CustomerRequestDTO customerRequestDTO`
- Response:
  - Uses `ResponseEntity.ok(response)`.
  - Returns HTTP `200 OK` with updated customer data.

### CategoryServiceImpl Notes

```java
Optional.of(request.getName())       // 1. wrap the raw name String (not the whole DTO)
    .map(String::trim)               // 2. remove leading/trailing whitespace
    .filter(name -> !name.isBlank()) // 3. if blank after trim → empty Optional → orElseThrow
    .filter(name -> !categoryRepository.existsByNameIgnoreCase(name)) // 4. if duplicate → empty Optional → orElseThrow
    .map(name -> {                   // 5. build Category entity
        Category c = new Category();
        c.setName(name);
        return c;
    })
    .map(categoryRepository::save)   // 6. persist to DB, returns saved entity with generated ID
    .map(saved -> new CategoryResponseDTO(saved.getId(), saved.getName())) // 7. map to response
    .orElseThrow(() -> new DuplicateResourceException(...)); // if any filter returned empty
```
