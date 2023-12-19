package ua.yehor.autolightbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the AutoLight Backend Application.
 */
@SpringBootApplication
public class AutolightBackendApplication {
    /**
     * The main method to start the AutoLight Backend Application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(AutolightBackendApplication.class, args);
    }
}
