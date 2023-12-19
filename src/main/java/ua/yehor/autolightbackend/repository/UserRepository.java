package ua.yehor.autolightbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yehor.autolightbackend.model.UserEntity;

import java.util.Optional;

/**
 * Repository interface for managing UserEntity objects in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user by their login name.
     *
     * @param login The login name of the user.
     * @return An Optional containing the UserEntity if found, otherwise empty.
     */
    Optional<UserEntity> findByLogin(String login);

    /**
     * Deletes a user by their login name.
     *
     * @param login The login name of the user to be deleted.
     */
    void deleteByLogin(String login);
}
