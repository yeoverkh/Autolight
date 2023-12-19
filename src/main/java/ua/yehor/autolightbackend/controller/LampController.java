package ua.yehor.autolightbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yehor.autolightbackend.dto.DeletionLampDto;
import ua.yehor.autolightbackend.dto.LampDto;
import ua.yehor.autolightbackend.dto.LampEditingDto;
import ua.yehor.autolightbackend.model.LampEntity;
import ua.yehor.autolightbackend.service.LampService;

import java.util.List;
import java.util.Set;

/**
 * REST controller managing operations related to lamps.
 * Endpoints for retrieving, adding, editing, and removing lamps.
 */
@RestController
@RequestMapping("/lamps")
@RequiredArgsConstructor
public class LampController {
    /**
     * Service handling lamp-related operations.
     */
    private final LampService lampService;

    /**
     * Endpoint to retrieve all lamps.
     *
     * @return ResponseEntity containing the list of all lamps
     */
    @GetMapping
    public ResponseEntity<List<LampEntity>> getAllLamps() {
        return ResponseEntity.ok(lampService.getAllLamps());
    }

    /**
     * Endpoint to retrieve all lamps associated with a specific device.
     *
     * @param deviceId The ID of the device
     * @return ResponseEntity containing the list of lamps associated with the device
     */
    @GetMapping("/{deviceId}")
    public ResponseEntity<List<LampEntity>> getAllDeviceLamps(@PathVariable Long deviceId) {
        return ResponseEntity.ok(lampService.getAllDeviceLamps(deviceId));
    }

    /**
     * Endpoint to add a new lamp to a device.
     *
     * @param lampDto The LampDto object representing the new lamp
     * @return ResponseEntity containing the set of lamps after addition
     */
    @PostMapping
    public ResponseEntity<Set<LampEntity>> addLampToDevice(@RequestBody LampDto lampDto) {
        return new ResponseEntity<>(lampService.saveLamp(lampDto), HttpStatus.CREATED);
    }

    /**
     * Endpoint to change the light level of a lamp.
     *
     * @param lampEditingDto The LampEditingDto object representing the changes
     * @return ResponseEntity containing the set of lamps after the light level change
     */
    @PatchMapping
    public ResponseEntity<Set<LampEntity>> changeLightLevelOfLamp(@RequestBody LampEditingDto lampEditingDto) {
        return new ResponseEntity<>(lampService.editLamp(lampEditingDto), HttpStatus.CREATED);
    }

    /**
     * Endpoint to remove a lamp from a device.
     *
     * @param deletionLampDto The DeletionLampDto object representing the lamp to be removed
     * @return ResponseEntity indicating the success of the removal operation
     */
    @DeleteMapping
    public ResponseEntity<Set<LampEntity>> removeLampFromDevice(@RequestBody DeletionLampDto deletionLampDto) {
        lampService.removeLampFromDevice(deletionLampDto);
        return ResponseEntity.noContent().build();
    }
}
