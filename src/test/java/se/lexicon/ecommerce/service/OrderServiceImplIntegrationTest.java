package se.lexicon.ecommerce.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import se.lexicon.ecommerce.model.Customer;
import se.lexicon.ecommerce.dto.request.OrderRequestDTO;
import se.lexicon.ecommerce.dto.request.OrderRequestDTO.OrderItemRequest;
import se.lexicon.ecommerce.dto.response.OrderResponseDTO;
import se.lexicon.ecommerce.model.Address;
import se.lexicon.ecommerce.model.Product;
import se.lexicon.ecommerce.model.Category;
import se.lexicon.ecommerce.repository.CustomerRepository;
import se.lexicon.ecommerce.repository.OrderRepository;
import se.lexicon.ecommerce.repository.ProductRepository;
import se.lexicon.ecommerce.repository.CategoryRepository;
import se.lexicon.ecommerce.repository.PromotionRepository;

@SpringBootTest
class OrderServiceImplIntegrationTest {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PromotionRepository promotionRepository;

    Long customerId;
    Long productId;

    Long customerId2;

    @BeforeEach
    void setup() {
        // Clean up all repositories to avoid unique constraint violations
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        promotionRepository.deleteAll();

        Category cat = new Category();
        cat.setName("Books");
        cat = categoryRepository.save(cat);

        Product product = new Product();
        product.setName("Test Book");
        product.setPrice(new BigDecimal("123.45"));
        product.setCategory(cat);
        product = productRepository.save(product);
        productId = product.getId();

        Address address = new Address();
        address.setStreet("Main St");
        address.setCity("City");
        address.setZipCode("12345");
        Customer customer = new Customer();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail("jane@example.com");
        customer.setAddress(address);
        customer = customerRepository.save(customer);
        customerId = customer.getId();

        // Add a second customer
        Address address2 = new Address();
        address2.setStreet("Second St");
        address2.setCity("Town");
        address2.setZipCode("54321");
        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Smith");
        customer2.setEmail("john@example.com");
        customer2.setAddress(address2);
        customer2 = customerRepository.save(customer2);
        customerId2 = customer2.getId();
    }

    @Test
    void placeOrder_persistsOrderAndItems() {
        OrderItemRequest itemReq = new OrderItemRequest(productId, 2);
        OrderRequestDTO req = new OrderRequestDTO(customerId, List.of(itemReq));
        OrderResponseDTO resp = orderService.placeOrder(req);

        assertThat(resp.orderId()).isNotNull();
        assertThat(resp.orderItems()).hasSize(1);
        assertThat(resp.orderItems().get(0).productId()).isEqualTo(productId);
        assertThat(resp.orderItems().get(0).quantity()).isEqualTo(2);
        assertThat(resp.customerName()).contains("Jane");
        assertThat(orderRepository.findById(resp.orderId())).isPresent();

        System.out.println("Customer ID: " + customerId);
        System.out.println("OrderResponse: " + resp);
        System.out.println("Order ID: " + resp.orderId());
        System.out.println("Customer Name: " + resp.customerName());
        System.out.println("Order Status: " + resp.orderStatus());
        System.out.println("Address: " + resp.addressResponse());
        resp.orderItems().forEach(item -> {
            System.out.println("  Item: productId=" + item.productId() + ", name=" + item.productName() + ", price="
                    + item.price() + ", qty=" + item.quantity());
        });
        System.out.flush();
    }

    @Test
    void placeOrder_withSecondCustomer_assignsUniqueId() {
        OrderItemRequest itemReq = new OrderItemRequest(productId, 1);
        OrderRequestDTO req = new OrderRequestDTO(customerId2, List.of(itemReq));
        OrderResponseDTO resp = orderService.placeOrder(req);

        assertThat(resp.orderId()).isNotNull();
        assertThat(resp.orderItems()).hasSize(1);
        assertThat(resp.orderItems().get(0).productId()).isEqualTo(productId);
        assertThat(resp.orderItems().get(0).quantity()).isEqualTo(1);
        assertThat(resp.customerName()).contains("John");
        assertThat(orderRepository.findById(resp.orderId())).isPresent();

        System.out.println("Customer ID: " + customerId2);
        System.out.println("OrderResponse: " + resp);
        System.out.println("Order ID: " + resp.orderId());
        System.out.println("Customer Name: " + resp.customerName());
        System.out.println("Order Status: " + resp.orderStatus());
        System.out.println("Address: " + resp.addressResponse());
        resp.orderItems().forEach(item -> {
            System.out.println("  Item: productId=" + item.productId() + ", name=" + item.productName() + ", price="
                    + item.price() + ", qty=" + item.quantity());
        });
        System.out.flush();
    }
}
