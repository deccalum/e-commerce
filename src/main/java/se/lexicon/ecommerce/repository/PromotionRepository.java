package se.lexicon.ecommerce.repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.lexicon.ecommerce.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, BigInteger> {

    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :date AND (p.endDate IS NULL OR p.endDate >= :date)")
    List<Promotion> findActiveOnDate(@Param("date") LocalDate date);
    Promotion findByCode(String code);
    List<Promotion> findByStartDateAfter(LocalDate startDate);
    List<Promotion> findByEndDateBefore(LocalDate endDate);
    List<Promotion> findByEndDateIsNull();
}