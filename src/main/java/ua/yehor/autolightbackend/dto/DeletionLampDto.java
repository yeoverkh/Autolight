package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for lamp deletion requests.
 * <p>
 * This record encapsulates information required for deleting a lamp,
 * specifically containing the device id and the name of the lamp to be deleted.
 * <p>
 * Fields:
 * - deviceId: Long representing the device's id associated with the lamp
 * - name: String representing the name of the lamp to be deleted
 */
public record DeletionLampDto(Long deviceId, String name) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (deviceId and name)
}
