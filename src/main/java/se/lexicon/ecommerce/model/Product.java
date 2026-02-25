
package se.lexicon.ecommerce.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.lexicon.ecommerce.model.Product_Promotion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", nullable = false, length = 500)
    private List<String> imageUrls;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_promotions", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private Set<Product_Promotion> promotions = new HashSet<>();
}