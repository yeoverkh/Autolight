package ua.yehor.autolightbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.yehor.autolightbackend.exception.RoleAlreadyExistsException;
import ua.yehor.autolightbackend.exception.UserNotContainsRoleException;
import ua.yehor.autolightbackend.model.Role;
import ua.yehor.autolightbackend.model.UserEntity;
import ua.yehor.autolightbackend.repository.UserRepository;

import java.util.List;

/**
 * Service class responsible for user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    /**
     * Repository for managing UserEntity objects.
     */
    private final UserRepository userRepository;

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The UserEntity representing the current user.
     * @throws UsernameNotFoundException if the user with the current login is not found.
     */
    public UserEntity getCurrentUser() {
        UserEntity currentUserEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity foundUserEntity = userRepository.findByLogin(currentUserEntity.getLogin()).orElse(null);

        if (foundUserEntity == null) {
            throw new UsernameNotFoundException("Cannot find user with this login");
        }

        return foundUserEntity;
    }

    /**
     * Retrieves a user by their login name.
     *
     * @param login The login name of the user to retrieve.
     * @return The UserEntity associated with the provided login.
     * @throws EntityNotFoundException if the user with the provided login is not found.
     */
    public UserEntity getByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Retrieves all users present in the system.
     *
     * @return A list of all UserEntity objects present in the system.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Saves a user to the repository.
     *
     * @param userEntity The UserEntity to be saved.
     * @return The saved UserEntity.
     */
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Deletes a user by their login name.
     *
     * @param login The login name of the user to be deleted.
     * @throws AccessDeniedException if the logged-in user is not authorized to delete another user.
     */
    @Transactional
    public void deleteByLogin(String login) throws AccessDeniedException {
        UserEntity userEntity = getCurrentUser();

        if (!userEntity.containsRole(Role.ADMIN)) {
            throw new AccessDeniedException("Unauthorized to remove user");
        }

        userRepository.deleteByLogin(login);
    }

    /**
     * Checks if a user is present in the system by their login name.
     *
     * @param login The login name of the user to check.
     * @return True if the user is present, false otherwise.
     */
    public boolean isUserPresentByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    /**
     * Adds a role to a user.
     *
     * @param login    The login name of the user to whom the role is to be added.
     * @param roleName The name of the role to add.
     * @return The updated UserEntity with the added role.
     */
    public UserEntity addRoleToUser(String login, String roleName) {
        UserEntity foundUser = getByLogin(login);

        Role newRole = Role.valueOf(roleName);

        if (foundUser.containsRole(newRole)) {
            throw new RoleAlreadyExistsException(roleName + " role is already exists in user roles.");
        }

        foundUser.addRole(newRole);

        return userRepository.save(foundUser);
    }

    /**
     * Removes a role from a user.
     *
     * @param login    The login name of the user from whom the role is to be removed.
     * @param roleName The name of the role to remove.
     * @return The updated UserEntity after removing the role.
     */
    public UserEntity removeRoleFromUser(String login, String roleName) {
        UserEntity foundUser = getByLogin(login);

        Role deletingRole = Role.valueOf(roleName);

        if (!foundUser.containsRole(deletingRole)) {
            throw new UserNotContainsRoleException("User doesn't have role " + roleName + '.');
        }

        foundUser.removeRole(deletingRole);

        return userRepository.save(foundUser);
    }
}
