package ua.yehor.autolightbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.yehor.autolightbackend.dto.DeletionLampDto;
import ua.yehor.autolightbackend.dto.LampDto;
import ua.yehor.autolightbackend.dto.LampEditingDto;
import ua.yehor.autolightbackend.model.DeviceEntity;
import ua.yehor.autolightbackend.model.LampEntity;
import ua.yehor.autolightbackend.repository.LampRepository;

import java.util.List;
import java.util.Set;

/**
 * Represents a lamp entity within the system.
 * This entity contains information about a lamp's ID, association with a device,
 * name, and light level.
 */
@Service
@RequiredArgsConstructor
public class LampService {
    /**
     * Repository for LampEntity to perform CRUD operations.
     */
    private final LampRepository lampRepository;

    /**
     * Service for DeviceEntity to perform device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Retrieves all lamps present in the system.
     *
     * @return A list of all LampEntity objects.
     */
    public List<LampEntity> getAllLamps() {
        return lampRepository.findAll();
    }

    /**
     * Retrieves all lamps associated with a specific device.
     *
     * @param deviceId The ID of the device.
     * @return A list of LampEntity objects associated with the specified device ID.
     */
    public List<LampEntity> getAllDeviceLamps(Long deviceId) {
        return lampRepository.findAllByDeviceId(deviceId);
    }

    /**
     * Saves a new lamp associated with a specific device.
     *
     * @param lampDto The LampDto object containing lamp details.
     * @return A set of LampEntity objects associated with the device after saving the new lamp.
     */
    public Set<LampEntity> saveLamp(LampDto lampDto) {
        DeviceEntity device = deviceService.getDeviceById(lampDto.deviceId());

        LampEntity lamp = new LampEntity(lampDto.name(), lampDto.lightLevel());

        lamp.setDevice(device);
        device.addLamp(lamp);

        return deviceService.saveDevice(device).getLamps();
    }

    /**
     * Edits the light level of a lamp associated with a specific device.
     *
     * @param lampEditingDto The LampEditingDto object containing lamp editing details.
     * @return A set of LampEntity objects associated with the device after editing the lamp.
     */
    public Set<LampEntity> editLamp(LampEditingDto lampEditingDto) {
        DeviceEntity device = deviceService.getDeviceById(lampEditingDto.deviceId());

        LampEntity lamp = device.getLamps().stream().filter(deviceLamp -> deviceLamp.getName().equals(lampEditingDto.name())).toList().get(0);

        device.removeLamp(lamp);

        lamp.setLightLevel(lampEditingDto.newValue());

        device.addLamp(lamp);

        return deviceService.saveDevice(device).getLamps();
    }

    /**
     * Removes a lamp from a specific device.
     *
     * @param deletionLampDto The DeletionLampDto object containing details to delete the lamp.
     */
    public void removeLampFromDevice(DeletionLampDto deletionLampDto) {
        DeviceEntity device = deviceService.getDeviceById(deletionLampDto.deviceId());

        LampEntity lamp = lampRepository.findByName(deletionLampDto.name());

        device.removeLamp(lamp);
        deviceService.saveDevice(device);
    }
}
