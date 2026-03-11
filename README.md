## `Optional.ofNullable()`

    Used to create an Optional object that may or may not contain a non-null
    value.
    If the provided value is non-null, it returns an Optional containing that
    value.
    If the provided value is null, it returns an empty Optional.
    This method is useful for handling potential null values without explicitly
    checking for null.
    It allows for more concise and readable code when dealing with nullable
    values.
    A way to avoid NullPointerException by wrapping potentially null values in
    an Optional, providing a more functional approach to handling nullability.

## BREAKDOWNS

### `placeOrder`
````java
return Optional.ofNullable(request): //Wraps request in an Optional. If null, Optional is empty and throws ...
.map(orderMapper::toEntity) //Converts OrderRequest DTO to Order entity. At this point the order exists but has no customer, order items, or other details set.
.map(order -> { ... }) //Opens a lambda to set additional details on the Order entity
order.setCustomer(...) //Fetches the Customer by ID from original request and sets it on the Order.
order.setOrderDate(Instant.now()) //Sets the current timestamp as the order date.
order.setStatus(OrderStatus.CREATED) //Sets the initial status of the order to CREATED.
order.setOrderItems(...) //Streams through the list of OrderItemRequest DTOs from the original request.
.<OrderItem>map((OrderItemRequest itemReq) -> { ... }) //Maps each item request into an OrderItem entity. The explicit <OrderItem> and OrderItemRequest type hints are there to help the compiler resolve the types inside this nested lambda.
Product product = productRepository.findById(itemReq.productId())`: Looks up the Product for this line item by its ID.
- `OrderItem item = new OrderItem();` Builds the OrderItem — wires in the product, copies the quantity, snapshots the price at the time of purchase, and back-references the parent order. Returns it.
````

  ```java
  item.setProduct(product);
  item.setQuantity(itemReq.quantity());
  item.setPriceAtPurchase(product.getPrice());
  item.setOrder(order);
  return item;
  ```

- `.toList()`: Collects all the built OrderItems into a list and sets it on the order. Returns the now-fully-populated Order out of the enrichment lambda.
- `.map(orderRepository::save)`: Persists the complete order (and its items, assuming cascade is configured) to the database.
- `.map(orderMapper::toResponse)`: Converts the saved Order entity into an OrderResponse DTO for the caller.
- `.orElseThrow(...)`: If at any point the initial request was null, or if any of the lookups failed (customer or product not found), an appropriate exception is thrown, signaling the failure to place the order.


## MAPSTRUCT

How it works:

- MapStruct generates mapper implementations at compile time from your
interface signatures and `@Mapping` rules.

- `toResponse` is for entity -> API DTO (flattening nested values like
`category.name -> categoryName`).

- `toEntity` is for request DTO -> entity skeleton (only input fields);
service fills business-managed fields and repository-managed relations.

- In your project, mapper should convert shape; service should handle
lookups/transactions/business logic.
