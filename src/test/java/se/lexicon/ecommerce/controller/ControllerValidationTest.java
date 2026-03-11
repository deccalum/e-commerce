package se.lexicon.ecommerce.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import se.lexicon.ecommerce.exception.GlobalExceptionHandler;
import se.lexicon.ecommerce.service.CategoryService;
import se.lexicon.ecommerce.service.CustomerService;
import se.lexicon.ecommerce.service.OrderService;
import se.lexicon.ecommerce.service.ProductService;

/**
 * Web-layer slice tests verifying that @Valid on all request DTOs
 * rejects invalid input with 400 Bad Request and a consistent error body.
 *
 * Controllers are tested via standalone MockMvc with mocked services.
 */
class ControllerValidationTest {

        MockMvc mockMvc;

        CustomerService customerService;

        ProductService productService;

        CategoryService categoryService;

        OrderService orderService;

        @BeforeEach
        void setup() {
                customerService = mock(CustomerService.class);
                productService = mock(ProductService.class);
                categoryService = mock(CategoryService.class);
                orderService = mock(OrderService.class);

                CustomerController customerController = new CustomerController(customerService);
                ProductController productController = new ProductController(productService);
                CategoryController categoryController = new CategoryController(categoryService);
                OrderController orderController = new OrderController(orderService);

                LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
                validator.afterPropertiesSet();

                mockMvc = MockMvcBuilders
                                .standaloneSetup(customerController, productController, categoryController,
                                                orderController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .setValidator(validator)
                                .build();
        }

        // --------------------------------------------------------------------------
        // Customer
        // --------------------------------------------------------------------------

        @Test
        void createCustomer_allBlankFields_returns400() throws Exception {
                String json = """
                                {
                                  "firstName": "",
                                  "lastName": "",
                                  "email": "not-an-email",
                                  "password": "weak",
                                  "street": "",
                                  "city": "",
                                  "zipCode": ""
                                }
                                """;

                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.status").value(400))
                                .andExpect(jsonPath("$.message").isNotEmpty());
        }

        @Test
        void createCustomer_invalidEmail_returns400() throws Exception {
                String json = """
                                {
                                  "firstName": "Anna",
                                  "lastName": "Svensson",
                                  "email": "not-valid",
                                  "password": "StrongP@ss1",
                                  "street": "Main St 1",
                                  "city": "Stockholm",
                                  "zipCode": "11122"
                                }
                                """;

                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createCustomer_weakPassword_returns400() throws Exception {
                // Fails @Size(min=8) and @Pattern
                String json = """
                                {
                                  "firstName": "Anna",
                                  "lastName": "Svensson",
                                  "email": "anna@example.com",
                                  "password": "weak",
                                  "street": "Main St 1",
                                  "city": "Stockholm",
                                  "zipCode": "11122"
                                }
                                """;

                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createCustomer_missingBody_returns400() throws Exception {
                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isBadRequest());
        }

        // --------------------------------------------------------------------------
        // Product
        // --------------------------------------------------------------------------

        @Test
        void createProduct_blankName_returns400() throws Exception {
                String json = """
                                {
                                  "name": "",
                                  "price": 100.00,
                                  "categoryId": 1
                                }
                                """;

                mockMvc.perform(post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createProduct_negativePrice_returns400() throws Exception {
                String json = """
                                {
                                  "name": "Keyboard",
                                  "price": -50.00,
                                  "categoryId": 1
                                }
                                """;

                mockMvc.perform(post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createProduct_missingCategoryId_returns400() throws Exception {
                String json = """
                                {
                                  "name": "Keyboard",
                                  "price": 499.00
                                }
                                """;

                mockMvc.perform(post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        // --------------------------------------------------------------------------
        // Category
        // --------------------------------------------------------------------------

        @Test
        void createCategory_blankName_returns400() throws Exception {
                String json = """
                                {
                                  "name": ""
                                }
                                """;

                mockMvc.perform(post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createCategory_nameTooLong_returns400() throws Exception {
                // @Size(max = 50) — 51 chars
                String json = """
                                {
                                  "name": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                                }
                                """;

                mockMvc.perform(post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        // --------------------------------------------------------------------------
        // Order
        // --------------------------------------------------------------------------

        @Test
        void placeOrder_emptyItems_returns400() throws Exception {
                String json = """
                                {
                                  "customerId": 1,
                                  "orderItems": []
                                }
                                """;

                mockMvc.perform(post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void placeOrder_negativeCustomerId_returns400() throws Exception {
                String json = """
                                {
                                  "customerId": -1,
                                  "orderItems": [
                                    { "productId": 1, "quantity": 1 }
                                  ]
                                }
                                """;

                mockMvc.perform(post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void placeOrder_zeroQuantity_returns400() throws Exception {
                // @Min(value = 1) on quantity
                String json = """
                                {
                                  "customerId": 1,
                                  "orderItems": [
                                    { "productId": 1, "quantity": 0 }
                                  ]
                                }
                                """;

                mockMvc.perform(post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void placeOrder_missingBody_returns400() throws Exception {
                mockMvc.perform(post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isBadRequest());
        }
}
