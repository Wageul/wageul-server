package com.wageul.wageul_server.common;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expire-length}")
    private long expireLength;

    public String generateToken(long id) {
        final Claims claims = Jwts.claims();
        claims.put("userId", id);
        final Date now = new Date();
        final Date expiredDate = new Date(now.getTime() - expireLength);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public long extractUserId(String token) {
        Jwt<Header, Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJwt(token);
        } catch (Exception ignored) {
            throw new RuntimeException("INVALID JWT ERROR");
        }

        return claims.getBody().get("userId", Long.class);
    }
}
