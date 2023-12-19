package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for device information.
 * <p>
 * This record encapsulates information about a device, typically used
 * for creating or updating a device, containing the user login and the name of the device.
 * <p>
 * Fields:
 * - userLogin: String representing the user's login associated with the device
 * - name: String representing the name of the device
 */
public record DeviceDto(String userLogin, String name) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (userLogin and name)
}
