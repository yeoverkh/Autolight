package ua.yehor.autolightbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yehor.autolightbackend.dto.DeletionDeviceDto;
import ua.yehor.autolightbackend.dto.DeviceDto;
import ua.yehor.autolightbackend.model.DeviceEntity;
import ua.yehor.autolightbackend.service.DeviceService;

import java.util.List;
import java.util.Set;

/**
 * Controller handling device-related endpoints.
 */
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {
    /**
     * Service responsible for device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Retrieves all devices associated with a particular user.
     *
     * @param userLogin User login ID
     * @return ResponseEntity containing a list of DeviceEntity associated with the user
     */
    @GetMapping("/{userLogin}")
    public ResponseEntity<List<DeviceEntity>> getAllUserDevices(@PathVariable String userLogin) {
        return ResponseEntity.ok(deviceService.getAllDevicesByUserLogin(userLogin));
    }

    /**
     * Saves a new device.
     *
     * @param deviceDto Device information to be saved
     * @return ResponseEntity containing a set of all DeviceEntity corresponding to user
     * and HTTP status CREATED
     */
    @PostMapping
    public ResponseEntity<Set<DeviceEntity>> saveDevice(@RequestBody DeviceDto deviceDto) {
        return new ResponseEntity<>(deviceService.saveDevice(deviceDto), HttpStatus.CREATED);
    }

    /**
     * Deletes a device from a user's list of devices.
     *
     * @param deletionDeviceDto Information about the device to be deleted from the user
     * @return ResponseEntity with no content and HTTP status NO_CONTENT
     */
    @DeleteMapping
    public ResponseEntity<List<DeviceEntity>> deleteDeviceFromUser(@RequestBody DeletionDeviceDto deletionDeviceDto) {
        deviceService.deleteDeviceFromUser(deletionDeviceDto);
        return ResponseEntity.ok(deviceService.getAllDevicesByUserLogin(deletionDeviceDto.userLogin()));
    }
}
