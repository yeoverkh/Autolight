package ua.yehor.autolightbackend.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.yehor.autolightbackend.service.ImportExportService;

import java.io.IOException;

/**
 * REST controller handling import and export operations for user data.
 * Endpoints for saving user data to and from CSV files.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ImportExportController {
    /**
     * Service for import and export operations.
     */
    private final ImportExportService importExportService;

    /**
     * Endpoint to save users' data to a CSV file.
     *
     * @return ResponseEntity indicating the success of the operation
     * @throws IOException If an I/O exception occurs while writing the CSV file
     */
    @GetMapping(value = "/save-to-csv", produces = "text/csv")
    public ResponseEntity<String> saveUsersToCSV() throws IOException {
        importExportService.saveUsersToCsv();

        return ResponseEntity.ok("Data successfully written to file");
    }

    /**
     * Endpoint to save users' data from a CSV file.
     *
     * @param file The CSV file containing user data
     * @return ResponseEntity indicating the success of the operation
     * @throws CsvValidationException If the CSV file doesn't adhere to CSV format
     * @throws IOException            If an I/O exception occurs while processing the file
     */
    @PostMapping("/save-from-csv")
    public ResponseEntity<String> saveUsersFromCSV(@RequestBody MultipartFile file) throws CsvValidationException, IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }

        importExportService.saveUsersFromCsv(file);
        return new ResponseEntity<>("Users saved from CSV successfully.", HttpStatus.CREATED);
    }
}
