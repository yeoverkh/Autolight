package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for authentication responses.
 * <p>
 * This record encapsulates the authentication response information, specifically
 * containing a token that might be generated upon successful authentication.
 * <p>
 * Fields:
 * - token: String representing the authentication token generated upon successful authentication
 */
public record AuthenticationResponseDto(String token) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (token)
}
