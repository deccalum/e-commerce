package se.lexicon.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.ecommerce.model.Address;
import se.lexicon.ecommerce.model.Customer;
import se.lexicon.ecommerce.repository.CustomerRepository;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindCustomer() {
        Address address = Address.builder()
                .street("Test Street")
                .city("Test City")
                .zipCode("12345")
                .build();
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setAddress(address);
        customer.setCreatedAt(Instant.now());
        Customer saved = customerRepository.save(customer);
        assertThat(saved.getId()).isNotNull();
        Customer found = customerRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("john.doe@example.com");
    }
}