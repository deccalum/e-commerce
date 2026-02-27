# PART 2

## FETCHTYPES

Determines when related objects are loaded from the database.

- LAZY: The related objects are not loaded until they are accessed.
- EAGER: The related objects are loaded immediately with the parent object.

### DEFAULTS

JPA defaults are:

- `@ManyToOne` -> `FetchType.EAGER`
- `@OneToOne` -> `FetchType.EAGER`
- `@OneToMany` -> `FetchType.LAZY`
- `@ManyToMany` -> `FetchType.LAZY`
- `@ElementCollection` -> `FetchType.LAZY`

Prefer explicit `LAZY` on relationships unless you always need the relation.

### Why EAGER can cause N+1 and slow endpoints

N+1 means one query is executed to load a parent list, then one additional query per row for related data.

Example:

1. Load 50 orders: `SELECT * FROM orders ...`
2. For each order, a relation is loaded (for example customer/items depending on mapping and usage).
3. Total queries become `1 + 50` (or more if multiple relations are touched).

Why this hurts:

- More SQL round-trips
- More DB CPU and network overhead
- Slower API response time
- Harder to predict performance as row count grows

How to avoid it:

- Keep associations `LAZY` by default.
- Use repository-level fetch plans when needed for specific use cases:
  - `@EntityGraph(attributePaths = "orderItems")`
  - JPQL `JOIN FETCH`

This gives controlled eager loading only where required.

## ANNOTATIONS

### @ElementCollection

Used for collections of non-entity values (basic types or embeddables).

Use this when the collection items are not their own `@Entity`.

- `List<String> tags;` - A collection of simple values (strings) that can be stored in a separate table.
- `Set<Integer> ratings;` - A collection of simple values (integers) that can be stored in a separate table.
- `Map<String, String> attributes;` - A collection of key-value pairs that can be stored in a separate table.

Do not use `@ElementCollection` for relationships like `Product`, `Category`, `Order`, etc. Use entity mappings (`@ManyToOne`, `@OneToMany`, `@ManyToMany`) instead.

### @CollectionTable

Used in conjunction with `@ElementCollection` to specify the table that will hold the collection of basic types or embeddable objects.

- `@CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))` - Specifies that the collection of tags will be stored in a table named "product_tags" with a foreign key column named "product_id" that references the parent entity.
- `@CollectionTable(name = "product_ratings", joinColumns = @JoinColumn(name = "product_id"))` - Specifies that the collection of ratings will be stored in a table named "product_ratings" with a foreign key column named "product_id" that references the parent entity.
- `@CollectionTable(name = "product_attributes", joinColumns = @JoinColumn(name = "product_id"))` - Specifies that the collection of attributes will be stored in a table named "product_attributes" with a foreign key column named "product_id" that references the parent entity.

In this project, `Product.imageUrls` is a valid `@ElementCollection` use case with its own collection table.

## PROJECT MAPPING CHEATSHEET

| Owner Entity | Field         | Annotation                                | DB Column / Table                              | Fetch             | Notes                                      |
| ------------ | ------------- | ----------------------------------------- | ---------------------------------------------- | ----------------- | ------------------------------------------ |
| `Product`    | `category`    | `@ManyToOne`                              | `category_id`                                  | `LAZY` (explicit) | Many products belong to one category       |
| `Order`      | `customer`    | `@ManyToOne`                              | `customer_id`                                  | `LAZY` (explicit) | Each order belongs to one customer         |
| `Order`      | `orderItems`  | `@OneToMany(mappedBy="order")`            | via `order_id` in `order_item`                 | `LAZY` (explicit) | Cascade + orphan removal enabled           |
| `OrderItem`  | `order`       | `@ManyToOne`                              | `order_id`                                     | `LAZY` (explicit) | Owner side for Order-OrderItem relation    |
| `OrderItem`  | `product`     | `@ManyToOne`                              | `product_id`                                   | `LAZY` (explicit) | References purchased product               |
| `Order`      | `status`      | `@Enumerated(EnumType.STRING)`            | `status`                                       | N/A               | Stores readable enum values                |
| `Product`    | `promotions`  | `@ManyToMany` + `@JoinTable`              | `products_promotions(product_id,promotion_id)` | `LAZY` (explicit) | Product is owner side                      |
| `Promotion`  | `products`    | `@ManyToMany(mappedBy="promotions")`      | inverse side                                   | `LAZY` (explicit) | Bidirectional optional side                |
| `Product`    | `imageUrls`   | `@ElementCollection` + `@CollectionTable` | `product_images(product_id,image_url)`         | `LAZY` (default)  | Value-type collection, not entity relation |
| `Customer`   | `address`     | `@OneToOne` + `@JoinColumn`               | `address_id`                                   | default (`EAGER`) | Mandatory in current model                 |
| `Customer`   | `userProfile` | `@OneToOne` + `@JoinColumn`               | `profile_id`                                   | default (`EAGER`) | Optional profile                           |

### Repository note for N+1

For order-status queries where items are needed, prefer:

- `@EntityGraph(attributePaths = "orderItems")` on repository methods, or
- `JOIN FETCH` JPQL

This keeps default loading lean while still supporting efficient read scenarios.
