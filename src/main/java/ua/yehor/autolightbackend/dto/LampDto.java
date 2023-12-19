package ua.yehor.autolightbackend.dto;

/**
 * Represents a data transfer object (DTO) for creating or updating a lamp.
 * Includes information about the device ID, name, and light level of the lamp.
 */
public record LampDto(Long deviceId, String name, Integer lightLevel) {
    // Record fields and constructor implicitly defined
}
