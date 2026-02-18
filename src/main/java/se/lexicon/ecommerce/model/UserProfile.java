package se.lexicon.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String nickname;

    @Column (nullable = false, length = 100)
    private String phoneNumber;

    @Column (length = 500)
    private String bio;

    @OneToOne (mappedBy = "userProfile")
    private Customer customer;

    public UserProfile() {
    }
}