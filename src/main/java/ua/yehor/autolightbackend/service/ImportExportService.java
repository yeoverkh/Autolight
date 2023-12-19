package ua.yehor.autolightbackend.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.yehor.autolightbackend.model.UserEntity;
import ua.yehor.autolightbackend.repository.UserRepository;

import java.io.*;
import java.util.List;

import static com.opencsv.ICSVWriter.*;

/**
 * Service responsible for importing and exporting user data to and from CSV files.
 */
@Service
@RequiredArgsConstructor
public class ImportExportService {
    /**
     * Repository to interact with user data.
     */
    private final UserRepository userRepository;

    /**
     * Saves users from a CSV file uploaded via MultipartFile.
     *
     * @param file The CSV file containing user data
     * @throws IOException            If an I/O exception occurs while processing the file
     * @throws CsvValidationException If the CSV file doesn't adhere to CSV format
     */
    public void saveUsersFromCsv(MultipartFile file) throws IOException, CsvValidationException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            String[] nextRecord;
            // Skip headers
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each row and create UserEntity objects.
                // If user already exists, then skipping him
                if (userRepository.findByLogin(nextRecord[0]).isPresent()) {
                    continue;
                }

                UserEntity user = new UserEntity(nextRecord[0], nextRecord[1]);

                userRepository.save(user);
            }
        }
    }

    /**
     * Saves users' data to a CSV file.
     *
     * @throws IOException If an I/O exception occurs while writing the CSV file
     */
    public void saveUsersToCsv() throws IOException {
        List<UserEntity> users = userRepository.findAll();


        try (Writer writer = new FileWriter("users.csv");
             CSVWriter csvWriter = new CSVWriter(writer,
                     DEFAULT_SEPARATOR,
                     NO_QUOTE_CHARACTER,
                     DEFAULT_ESCAPE_CHARACTER,
                     DEFAULT_LINE_END)) {

            String[] headers = {"login", "password_hash"};
            csvWriter.writeNext(headers);

            // Going through all users in database and save them to csv file.
            for (UserEntity user : users) {
                String[] userData = {
                        user.getLogin(),
                        user.getPassword()
                };
                csvWriter.writeNext(userData);
            }
            csvWriter.flush();
        }
    }
}
