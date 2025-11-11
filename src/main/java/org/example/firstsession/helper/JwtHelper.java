package org.example.firstsession.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.firstsession.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtHelper {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.expiration}")
    private long EXPIRATION_TIME;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(
            User userDetails
    ) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userDetails.getUserId());
        extraClaims.put("name", userDetails.getName());
        extraClaims.put("email", userDetails.getEmail());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Method to validate the token
    public boolean isTokenValid(String token, User userDetails) {
        final String email = extractSubject(token);
        return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }
}

