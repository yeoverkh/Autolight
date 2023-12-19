package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for device deletion requests.
 * <p>
 * This record encapsulates information required for deleting a device,
 * specifically containing the user login and the name of the device to be deleted.
 * <p>
 * Fields:
 * - userLogin: String representing the user's login associated with the device
 * - name: String representing the name of the device to be deleted
 */
public record DeletionDeviceDto(String userLogin, String name) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (userLogin and name)
}
