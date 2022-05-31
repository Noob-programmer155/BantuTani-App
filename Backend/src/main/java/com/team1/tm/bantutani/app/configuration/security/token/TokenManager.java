package com.team1.tm.bantutani.app.configuration.security.token;

import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.other.Status;
import com.team1.tm.bantutani.app.repository.UserRepo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private static long time = 86400000*7;
    private static PrivateKey privateKey;

    @Autowired
    private UserRepo userRepo;
    @Scheduled(fixedRate = 86400000*7)
    private void generateToken() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
    }

    private static String createToken(String username, Long id, String email, Status status) {
        Date date = new Date();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("email", email);
        claims.put("role", status);
        claims.put("idx",id);
        return Jwts.builder().
                setClaims(claims).
                setIssuedAt(date).
                setExpiration(new Date(date.getTime()+time)).
                signWith(privateKey,SignatureAlgorithm.RS512).
                compact();
    }

    public static void bindToken(String username, Long id, String email, Status status, HttpServletResponse response) {
        String token = createToken(username, id, email, status);
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+token);
    }

    public Authentication getAuth(String token, HttpServletRequest request) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token);
            String name = claims.getBody().getSubject();
            User user = userRepo.findByUsername(name).get();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(user.getStatus()));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return authenticationToken;
        } catch (Exception e) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token != null)
            return token.substring(7);
        else
            throw new RuntimeException("Credential's Failed");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token);
            if (claims != null) {
                if(claims.getBody().getSubject() != null)
                    if(claims.getBody().containsKey("email"))
                        if(claims.getBody().containsKey("role"))
                            if(claims.getBody().containsKey("idx"))
                                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
