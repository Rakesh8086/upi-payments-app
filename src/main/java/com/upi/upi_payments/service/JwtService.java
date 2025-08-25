package com.upi.upi_payments.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // This value is a secret key used to sign and verify JWTs.
    // It is injected from the application.properties file.
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // This value represents how long the JWT will be valid (in milliseconds).
    // It is also injected from the application.properties file.
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    /**
     * Extracts the username (phone number) from a JWT token.
     * @param token The JWT token string.
     * @return The username (phone number).
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a single claim from a JWT token.
     * @param token The JWT token string.
     * @param claimsResolver A function to resolve a specific claim.
     * @param <T> The type of the claim.
     * @return The resolved claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a given user.
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return The signed JWT token string.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims and user details.
     * @param extraClaims Additional claims to include in the token payload.
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return The signed JWT token string.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token.
     * @param token The JWT token string.
     * @param userDetails The UserDetails object to validate against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if a token has expired.
     * @param token The JWT token string.
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a token.
     * @param token The JWT token string.
     * @return The expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT token.
     * @param token The JWT token string.
     * @return The Claims object containing all claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gets the signing key for the JWT.
     * @return The signing key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
