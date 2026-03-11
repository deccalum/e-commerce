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
