package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for authentication requests.
 * <p>
 * This record encapsulates authentication request information, specifically
 * containing a login and password pair used for authentication purposes.
 * <p>
 * Fields:
 * - login: String representing the user's login credential
 * - password: String representing the user's password credential
 */
public record AuthenticationRequestDto(String login, String password) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (login and password)
}
