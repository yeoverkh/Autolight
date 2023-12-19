package ua.yehor.autolightbackend.exception;

/**
 * Custom exception indicating that a role already exists.
 * <p>
 * This exception is thrown when attempting to create a role that already exists in the system.
 */
public class RoleAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a RoleAlreadyExistsException with the provided error message.
     *
     * @param message Error message describing the role already existing
     */
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}