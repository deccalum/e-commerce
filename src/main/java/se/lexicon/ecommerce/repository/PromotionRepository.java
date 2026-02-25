package se.lexicon.ecommerce.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.lexicon.ecommerce.model.Promotion;

/**
 * Repository for {@link Promotion} persistence and promotion-date queries.
 */
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * Finds promotions active on a specific date.
     *
     * @param date date to evaluate
     * @return promotions active on the date
     */
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :date AND (p.endDate IS NULL OR p.endDate >= :date)")
    List<Promotion> findActiveOnDate(@Param("date") LocalDate date);

    /**
     * Finds a promotion by exact code.
     *
     * @param code promotion code
     * @return matching promotion or null
     */
    Promotion findByCode(String code);

    /**
     * Finds promotions starting after a date.
     *
     * @param startDate lower bound start date
     * @return matching promotions
     */
    List<Promotion> findByStartDateAfter(LocalDate startDate);

    /**
     * Finds promotions ending before a date.
     *
     * @param endDate upper bound end date
     * @return matching promotions
     */
    List<Promotion> findByEndDateBefore(LocalDate endDate);

    /**
     * Finds promotions without an end date.
     *
     * @return open-ended promotions
     */
    List<Promotion> findByEndDateIsNull();
}