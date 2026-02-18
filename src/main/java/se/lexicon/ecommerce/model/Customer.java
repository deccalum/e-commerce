package se.lexicon.ecommerce.model;

import java.time.Instant;

public class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdAt;
    private Address address;
    private UserProfile userProfile;

}