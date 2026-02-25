package se.lexicon.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lexicon.ecommerce.model.UserProfile;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link UserProfile} persistence and profile-specific queries.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Finds a user profile by exact nickname.
     *
     * @param nickname nickname to match
     * @return optional profile
     */
    Optional<UserProfile> findByNickname(String nickname);

    /**
     * Finds profiles where phone number contains the provided value.
     *
     * @param partialPhoneNumber phone number fragment
     * @return matching profiles
     */
    List<UserProfile> findByPhoneNumberContaining(String partialPhoneNumber);

    /**
     * Finds profiles with non-null biography.
     *
     * @return profiles that have bio text
     */
    List<UserProfile> findByBioIsNotNull();

    /**
     * Finds profiles whose nickname starts with the given prefix.
     *
     * @param prefix nickname prefix
     * @return matching profiles
     */
    List<UserProfile> findByNicknameStartingWith(String prefix);

    /**
     * Counts profiles with phone numbers starting with the provided prefix.
     *
     * @param phoneNumberPrefix phone number prefix
     * @return profile count
     */
    long countByPhoneNumberStartingWith(String phoneNumberPrefix);
}