package ua.yehor.autolightbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.yehor.autolightbackend.dto.AuthenticationRequestDto;
import ua.yehor.autolightbackend.dto.AuthenticationResponseDto;
import ua.yehor.autolightbackend.service.AuthenticationService;

/**
 * Controller handling user authentication-related endpoints such as registration and login.
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    /**
     * Service responsible for authentication-related operations.
     */
    private final AuthenticationService authenticationService;

    /**
     * Endpoint for user registration.
     *
     * @param registerRequest AuthenticationRequestDto containing registration details
     * @return ResponseEntity containing the authentication response DTO and HTTP status CREATED
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody AuthenticationRequestDto registerRequest) {
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.CREATED);
    }

    /**
     * Endpoint for user login.
     *
     * @param authRequest AuthenticationRequestDto containing login credentials
     * @return ResponseEntity containing the authentication response DTO and HTTP status OK
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
