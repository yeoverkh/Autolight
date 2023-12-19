package ua.yehor.autolightbackend.exception;

/**
 * Custom exception indicating that a user does not contain a specific role.
 * <p>
 * This exception is thrown when attempting to perform an operation related to a role
 * that a user does not possess or is not associated with.
 */
public class UserNotContainsRoleException extends RuntimeException {
    /**
     * Constructs a UserNotContainsRoleException with the provided error message.
     *
     * @param message Error message indicating the absence of a specific role for the user
     */
    public UserNotContainsRoleException(String message) {
        super(message);
    }
}
