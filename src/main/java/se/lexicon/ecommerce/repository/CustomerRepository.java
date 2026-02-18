package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.Customer;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    List<Customer> findByLastNameIgnoreCase(String lastName);
    List<Customer> findByAddress_City(String city);
    List<Customer> findByEmailContainingIgnoreCase(String keyword);
    List<Customer> findByCreatedAtAfter(Instant date);
    List<Customer> findByCreatedAtBetween(Instant startDate, Instant endDate);
    long countByAddress_City(String city);
    boolean existsByEmail(String email);
}