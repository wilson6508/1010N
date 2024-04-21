package com.caili.todolist.util;

import io.jsonwebtoken.*;

import javax.security.auth.message.AuthException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtToken implements Serializable {

    private static final long EXPIRATION_TIME = 60 * 1000;
    private static final String SECRET = "learn";

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static void validateToken(String token) throws AuthException {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new AuthException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new AuthException("JWT token compact of handler are invalid");
        }
        System.out.println("pass");
    }

}
