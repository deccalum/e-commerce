package se.lexicon.ecommerce.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
