package com.masoud.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private  String jwtSecret;
    @Value("${jwt.expiration}")
    private Long JwtExpiration;

    private Key key;
    @PostConstruct
    public void init() {
        String secret= Encoders.BASE64.encode(jwtSecret.getBytes());
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JwtExpiration);
        return Jwts.builder().subject(username).
                issuedAt(now).
                expiration(expiredDate).
                signWith(key).
                compact();
    }

    public String getusernamefromjwt(String token)
    {
        Claims claims = Jwts.parser().
                setSigningKey(key).
                build().
                parseClaimsJws(token).
                getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;

        }
        catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token " + token+"=>"+e.getMessage());
            return false;
        }

    }

}
