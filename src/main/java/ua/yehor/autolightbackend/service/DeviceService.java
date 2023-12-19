package ua.yehor.autolightbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.yehor.autolightbackend.dto.DeletionDeviceDto;
import ua.yehor.autolightbackend.dto.DeviceDto;
import ua.yehor.autolightbackend.model.DeviceEntity;
import ua.yehor.autolightbackend.model.UserEntity;
import ua.yehor.autolightbackend.repository.DeviceRepository;

import java.util.List;
import java.util.Set;

/**
 * Service class responsible for device-related operations.
 */
@Service
@RequiredArgsConstructor
public class DeviceService {
    /**
     * Repository for managing DeviceEntity objects.
     */
    private final DeviceRepository deviceRepository;

    /**
     * Service for user-related operations.
     */
    private final UserService userService;

    /**
     * Retrieves a device by its ID.
     *
     * @param deviceId The ID of the device to retrieve.
     * @return The DeviceEntity associated with the provided ID.
     * @throws EntityNotFoundException if the device with the given ID is not found.
     */
    public DeviceEntity getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Retrieves all devices associated with a specific user by their login.
     *
     * @param userLogin The login name of the user.
     * @return A list of DeviceEntity objects associated with the user.
     */
    public List<DeviceEntity> getAllDevicesByUserLogin(String userLogin) {
        return deviceRepository.findAllByUserLogin(userLogin);
    }

    /**
     * Saves a device based on the provided DeviceDto.
     *
     * @param deviceDto The DeviceDto containing device information.
     * @return The set of DeviceEntity objects associated with the user after saving.
     */
    public Set<DeviceEntity> saveDevice(DeviceDto deviceDto) {
        DeviceEntity device = new DeviceEntity(deviceDto.name());

        UserEntity user = userService.getByLogin(deviceDto.userLogin());

        device.setUser(user);
        user.addDevice(device);

        return userService.save(user).getDevices();
    }

    /**
     * Saves a device.
     *
     * @param device The DeviceEntity to be saved.
     * @return The saved DeviceEntity.
     */
    public DeviceEntity saveDevice(DeviceEntity device) {
        return deviceRepository.save(device);
    }

    /**
     * Deletes a device associated with a specific user based on the provided DeletionDeviceDto.
     *
     * @param deviceDto The DeletionDeviceDto containing device information for deletion.
     */
    public void deleteDeviceFromUser(DeletionDeviceDto deviceDto) {
        UserEntity user = userService.getByLogin(deviceDto.userLogin());

        DeviceEntity device = deviceRepository.findByNameAndUser(deviceDto.name(), user);

        deviceRepository.delete(device);
        user.removeDevice(device);
    }
}
