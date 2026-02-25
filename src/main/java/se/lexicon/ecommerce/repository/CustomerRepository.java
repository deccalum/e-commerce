package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.Customer;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Customer} persistence and customer-focused queries.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by exact email.
     *
     * @param email customer email
     * @return optional customer
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Finds customers by last name, case-insensitive.
     *
     * @param lastName last name to match
     * @return matching customers
     */
    List<Customer> findByLastNameIgnoreCase(String lastName);

    /**
     * Finds customers by city via nested address property.
     *
     * @param city city name
     * @return matching customers
     */
    List<Customer> findByAddress_City(String city);

    /**
     * Finds customers whose email contains a keyword, case-insensitive.
     *
     * @param keyword email keyword
     * @return matching customers
     */
    List<Customer> findByEmailContainingIgnoreCase(String keyword);

    /**
     * Finds customers created after a given timestamp.
     *
     * @param date lower bound timestamp
     * @return matching customers
     */
    List<Customer> findByCreatedAtAfter(Instant date);

    /**
     * Finds customers created between two timestamps.
     *
     * @param startDate inclusive lower bound
     * @param endDate   inclusive upper bound
     * @return matching customers
     */
    List<Customer> findByCreatedAtBetween(Instant startDate, Instant endDate);

    /**
     * Counts customers in a given city.
     *
     * @param city city name
     * @return customer count
     */
    long countByAddress_City(String city);

    /**
     * Checks whether a customer exists by email.
     *
     * @param email customer email
     * @return true if a customer exists
     */
    boolean existsByEmail(String email);
}