package ua.yehor.autolightbackend.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Constant defining the start of an authentication header with a Bearer token.
     */
    public static final String AUTHENTICATION_HEADER_START = "Bearer ";

    /**
     * Service for handling JWT-related operations.
     */
    private final JwtService jwtService;

    /**
     * Service for retrieving user details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to perform JWT-based authentication.
     *
     * @param request     The incoming HTTP request
     * @param response    The HTTP response
     * @param filterChain The filter chain for continuing the request/response flow
     * @throws ServletException If an exception occurs within the servlet
     * @throws IOException      If an I/O exception occurs
     */
    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Extracts the Authorization header from the incoming request
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String login;

        // Checks if the Authorization header is absent or doesn't start with the expected format
        if (authenticationHeader == null || !authenticationHeader.startsWith(AUTHENTICATION_HEADER_START)) {
            // If not, continues the request/response flow
            filterChain.doFilter(request, response);
            return;
        }

        // Extracts the JWT token from the Authorization header
        jwtToken = authenticationHeader.substring(AUTHENTICATION_HEADER_START.length());
        // Extracts the user login from the JWT token
        login = jwtService.extractUsername(jwtToken);

        // Performs user authentication if the user login exists and authentication hasn't been set
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Loads user details based on the extracted login
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            // Validates the token against the user details retrieved
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // Constructs an authentication token and sets it in the SecurityContext
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        // Continues the request/response flow
        filterChain.doFilter(request, response);
    }
}
