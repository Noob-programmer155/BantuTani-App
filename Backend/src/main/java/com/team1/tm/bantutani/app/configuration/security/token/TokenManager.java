package com.team1.tm.bantutani.app.configuration.security.token;

import com.team1.tm.bantutani.app.model.other.Status;
import io.jsonwebtoken.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Date;

@Component
public class TokenManager {
    private long time = 86400000*7;
    private PrivateKey privateKey;
    @Scheduled(fixedRate = 86400000*7)
    private void generateToken() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
    }

    private String createToken(String username, String email, Status status) {
        Date date = new Date();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("email", email);
        claims.put("role", status);
        return Jwts.builder().
                setClaims(claims).
                setIssuedAt(date).
                setExpiration(new Date(date.getTime()+time)).
                signWith(privateKey,SignatureAlgorithm.RS512).
                compact();
    }

    public void bindToken(String username, String email, Status status, HttpServletResponse response) {
        String token = createToken(username, email, status);
        response.addHeader("S_TOKEN",token);
    }

    public Authentication getAuth(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token);
        return new UsernamePasswordAuthenticationToken(claims.getBody().getSubject(),
                "",
                Arrays.asList((Status) claims.getBody().get("role")));
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("S_TOKEN");
        if(token != null)
            return token;
        else
            throw new RuntimeException("Credential's Failed");
    }

    public boolean validateToken(String token) {
        try {
            Jwt obj = Jwts.parserBuilder().setSigningKey(privateKey).build().parse(token);
            if (obj != null)
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
