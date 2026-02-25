package se.lexicon.ecommerce.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder

@Entity
@Table (name = "addresses")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String street;

    @Column (nullable = false)
    private String city;

    @Column (nullable = false)
    private String zipCode;
}