package ua.yehor.autolightbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.yehor.autolightbackend.dto.ReadingDto;
import ua.yehor.autolightbackend.model.DeviceEntity;
import ua.yehor.autolightbackend.model.ReadingEntity;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class responsible for reading-related operations.
 */
@Service
@RequiredArgsConstructor
public class ReadingService {
    /**
     * Service for device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Retrieves all readings associated with a specific device by its ID.
     *
     * @param deviceId The ID of the device.
     * @return A set of ReadingEntity objects associated with the device.
     */
    public Set<ReadingEntity> getAllDeviceReadings(Long deviceId) {
        DeviceEntity device = deviceService.getDeviceById(deviceId);

        return device.getReadings();
    }

    /**
     * Saves a reading based on the provided ReadingDto.
     *
     * @param readingDto The ReadingDto containing reading information.
     * @return The set of ReadingEntity objects associated with the device after saving.
     */
    public Set<ReadingEntity> saveReading(ReadingDto readingDto) {
        ReadingEntity reading = new ReadingEntity(readingDto.name(), readingDto.value(), readingDto.isWarning());

        DeviceEntity device = deviceService.getDeviceById(readingDto.deviceId());

        reading.setDevice(device);
        device.addReading(reading);

        return deviceService.saveDevice(device).getReadings();
    }

    /**
     * Retrieves all warning readings associated with devices owned by a specific user.
     *
     * @param userLogin The login name of the user.
     * @return A set of ReadingEntity objects considered as warnings for the user's devices.
     */
    public Set<ReadingEntity> getAllUserWarnings(String userLogin) {
        return deviceService.getAllDevicesByUserLogin(userLogin).stream()
                .flatMap(device -> device.getReadings().stream())
                .filter(ReadingEntity::isWarning)
                .collect(Collectors.toSet());
    }
}
