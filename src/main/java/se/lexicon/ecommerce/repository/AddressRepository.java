package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.Address;

import java.util.List;

/**
 * Repository for {@link Address} persistence and address-specific lookup
 * queries.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Finds addresses by exact zip code.
     *
     * @param zipCode zip code to match
     * @return matching addresses
     */
    List<Address> findByZipCode(String zipCode);

    /**
     * Finds addresses by city name.
     *
     * @param city city name to match
     * @return matching addresses
     */
    List<Address> findByCity(String city);

    /**
     * Finds addresses whose zip code starts with the given prefix.
     *
     * @param zipCodePrefix zip code prefix
     * @return matching addresses
     */
    List<Address> findByZipCodeStartingWith(String zipCodePrefix);

    /**
     * Counts addresses with the given zip code.
     *
     * @param zipCode zip code to count
     * @return number of matching addresses
     */
    long countByZipCode(String zipCode);
}