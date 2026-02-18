package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByZipCode(String zipCode);
    List<Address> findByCity(String city);
    List<Address> findByZipCodeStartingWith(String zipCodePrefix);
    long countByZipCode(String zipCode);
}