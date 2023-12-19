package ua.yehor.autolightbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.yehor.autolightbackend.model.UserEntity;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service handling JWT-related operations.
 */
@Service
public class JwtService {
    /**
     * Secret key used to sign and verify JWT tokens.
     */
    @Value("${secretKey}")
    private String secretKey;

    /**
     * Expiration time for the JWT tokens, specified in milliseconds.
     */
    @Value("${expirationTimeInMilliseconds}")
    private long expirationTimeInMilliseconds;

    /**
     * Generates a JWT token for the provided UserDetails.
     *
     * @param userDetails The UserDetails object containing user-specific information
     * @return The generated JWT token as a String
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with provided claims for the UserDetails.
     *
     * @param claims      The additional claims to include in the token
     * @param userDetails The UserDetails object for which the token is generated
     * @return The generated JWT token as a String
     */
    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMilliseconds))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token
     * @return The extracted username as a String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided resolver function.
     *
     * @param <T>            The type of the claim to be extracted
     * @param token          The JWT token
     * @param claimsResolver The function to resolve the specific claim from the token's Claims
     * @return The extracted claim value of type T
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves all claims from the JWT token.
     *
     * @param token The JWT token
     * @return The extracted Claims object containing all claims from the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the provided JWT token is valid for the given UserDetails.
     *
     * @param token       The JWT token
     * @param userDetails The UserDetails object for which the token's validity is checked
     * @return True if the token is valid for the UserDetails, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the provided JWT token has expired.
     *
     * @param token The JWT token
     * @return True if the token has expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token
     * @return The extracted expiration date as a Date object
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
