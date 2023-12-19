package ua.yehor.autolightbackend.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.yehor.autolightbackend.dto.AuthenticationRequestDto;
import ua.yehor.autolightbackend.dto.AuthenticationResponseDto;
import ua.yehor.autolightbackend.jwt.JwtService;
import ua.yehor.autolightbackend.model.UserEntity;

/**
 * Service class responsible for user authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    /**
     * Service for user-related operations.
     */
    private final UserService userService;

    /**
     * Encoder for password hashing and verification.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Service for JWT-related operations.
     */
    private final JwtService jwtService;

    /**
     * Spring Security's AuthenticationManager for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param registerRequest The request containing user registration information.
     * @return An AuthenticationResponseDto containing a JWT token upon successful registration.
     * @throws EntityExistsException if a user with the provided login already exists.
     */
    public AuthenticationResponseDto register(AuthenticationRequestDto registerRequest) {
        if (userService.isUserPresentByLogin(registerRequest.login())) {
            throw new EntityExistsException("Entity with this login already exists");
        }

        UserEntity userEntity = new UserEntity(
                registerRequest.login(),
                passwordEncoder.encode(registerRequest.password())
        );

        userService.save(userEntity);

        String jwt = jwtService.generateToken(userEntity);

        return new AuthenticationResponseDto(jwt);
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param authRequest The request containing user authentication information.
     * @return An AuthenticationResponseDto containing a JWT token upon successful authentication.
     */
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.login(), authRequest.password()));

        UserEntity userEntity = userService.getByLogin(authRequest.login());

        String jwt = jwtService.generateToken(userEntity);

        return new AuthenticationResponseDto(jwt);
    }
}
