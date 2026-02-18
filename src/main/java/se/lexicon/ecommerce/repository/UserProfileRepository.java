package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.UserProfile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByNickname(String nickname);
    List<UserProfile> findByPhoneNumberContaining(String partialPhoneNumber);
    List<UserProfile> findByBioIsNotNull();
    List<UserProfile> findByNicknameStartingWith(String prefix);
    long countByPhoneNumberStartingWith(String phoneNumberPrefix);
}