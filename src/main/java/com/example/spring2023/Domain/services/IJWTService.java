package com.example.spring2023.Domain.services;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

public interface IJWTService {
    /**
     *
     * @param token - token to extract login from
     * @return login
     */
     String extractUserLogin(String token);

    /**
     *
     * @param userDetails - user details to encrypt in token
     * @return new token
     */

     String generateToken(UserDetails userDetails);

    /**
     *
     * @param token - token to extract claim from
     * @param claimsResolver - claim resolver to use as a resolver
     * @return - extracted claim
     */
     <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     *
     * @param extraClaims - extra claims to encrypt in token
     * @param userDetails -  user details to encrypt in token
     * @return new token
     */
     String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    /**
     *
     * @param token - token to check
     * @param userDetails - user details
     * @return boolean indicating whether token is valid
     */
     boolean isTokenValid(String token, UserDetails userDetails);
}
