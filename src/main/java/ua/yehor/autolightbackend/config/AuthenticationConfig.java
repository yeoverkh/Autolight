package ua.yehor.autolightbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.yehor.autolightbackend.repository.UserRepository;

/**
 * Configuration class responsible for setting up authentication-related beans
 * such as UserDetailsService, PasswordEncoder, AuthenticationProvider, and AuthenticationManager.
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    /**
     * Repository responsible for user data access.
     */
    private final UserRepository userRepository;

    /**
     * Creates a UserDetailsService bean used for retrieving user details during authentication.
     *
     * @return UserDetailsService implementation fetching user details from UserRepository
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return login -> userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with this login"));
    }

    /**
     * Provides a PasswordEncoder bean for encoding and decoding user passwords.
     *
     * @return PasswordEncoder implementation using BCrypt hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures an AuthenticationProvider using the provided PasswordEncoder and UserDetailsService.
     *
     * @param passwordEncoder the PasswordEncoder for encoding passwords
     * @return AuthenticationProvider configured with UserDetailsService and PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

    /**
     * Creates an AuthenticationManager bean for managing authentication processes.
     *
     * @param config AuthenticationConfiguration instance for retrieving the AuthenticationManager
     * @return AuthenticationManager instance for handling authentication requests
     * @throws Exception if there is an issue retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
