package ua.yehor.autolightbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ua.yehor.autolightbackend.controller.CustomExceptionHandlerController;
import ua.yehor.autolightbackend.jwt.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration class responsible for configuring security settings including authentication,
 * authorization rules, exception handling, and filters for HTTP requests.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * Filter responsible for JWT-based authentication.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Provider responsible for authenticating users based on custom configurations.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Controller handling custom exceptions for security-related issues.
     */
    private final CustomExceptionHandlerController exceptionHandlerController;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
                ));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean(new CorsFilter(source));
        filterRegistration.setOrder(-102);
        return filterRegistration;
    }

    /**
     * Configures the security filter chain defining authentication, authorization rules,
     * exception handling, and filters for HTTP requests.
     *
     * @param http HttpSecurity object to configure security settings
     * @return SecurityFilterChain defining security configurations
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                                "/register", "/login").permitAll()
                        .requestMatchers("/users/**").hasAuthority("ADMIN")
                        .requestMatchers("/devices/**", "/lamps/**").hasAuthority("TECHNICIAN")
                        .anyRequest().authenticated())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(exceptionHandlerController)
                        .accessDeniedHandler(exceptionHandlerController))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}