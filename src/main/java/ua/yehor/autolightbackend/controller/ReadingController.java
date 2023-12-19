package ua.yehor.autolightbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yehor.autolightbackend.dto.ReadingDto;
import ua.yehor.autolightbackend.model.ReadingEntity;
import ua.yehor.autolightbackend.service.ReadingService;

import java.util.Set;

/**
 * Controller managing endpoints related to readings.
 */
@RestController
@RequestMapping("/readings")
@RequiredArgsConstructor
public class ReadingController {
    /**
     * Service handling reading-related operations.
     */
    private final ReadingService readingService;

    /**
     * Retrieves all readings associated with a particular device.
     *
     * @param deviceId Device ID
     * @return ResponseEntity containing a set of ReadingEntity associated with the device
     */
    @GetMapping("/{deviceId}")
    public ResponseEntity<Set<ReadingEntity>> getAllDeviceReadings(@PathVariable Long deviceId) {
        return ResponseEntity.ok(readingService.getAllDeviceReadings(deviceId));
    }

    /**
     * Retrieves all warnings associated with a specific user.
     *
     * @param userLogin User login ID
     * @return ResponseEntity containing a set of ReadingEntity representing user warnings
     */
    @GetMapping("/warnings/{userLogin}")
    public ResponseEntity<Set<ReadingEntity>> getAllUserWarnings(@PathVariable String userLogin) {
        return ResponseEntity.ok(readingService.getAllUserWarnings(userLogin));
    }

    /**
     * Saves a new reading.
     *
     * @param readingDto Reading information to be saved
     * @return ResponseEntity containing a set of saved ReadingEntity and HTTP status CREATED
     */
    @PostMapping
    public ResponseEntity<Set<ReadingEntity>> saveReading(@RequestBody ReadingDto readingDto) {
        return new ResponseEntity<>(readingService.saveReading(readingDto), HttpStatus.CREATED);
    }
}
