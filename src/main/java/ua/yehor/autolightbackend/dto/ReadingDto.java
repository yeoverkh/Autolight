package ua.yehor.autolightbackend.dto;

/**
 * Represents a Data Transfer Object (DTO) for reading information.
 * <p>
 * This record encapsulates information about a reading, typically used
 * for recording or updating a reading, containing the device ID, reading name, value,
 * and a flag indicating if it's a warning.
 * <p>
 * Fields:
 * - deviceId: Long representing the ID of the device associated with the reading
 * - name: String representing the name or identifier of the reading
 * - value: Integer representing the value captured in the reading
 * - isWarning: Boolean indicating if the reading is considered a warning
 */
public record ReadingDto(Long deviceId, String name, Integer value, Boolean isWarning) {
    // No need for explicit constructor, accessor methods, equals, hashCode, or toString
    // The record implicitly provides these based on its components (deviceId, name, value, isWarning)
}
