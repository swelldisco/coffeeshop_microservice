package com.java_coffee.user_service.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTService.class);
    
    private static final String ALGORITHM = "HmacSHA256";
    private static final long EXPIRATION_TIME = 86400000L;
    private static final String PREFIX ="Bearer ";
    private static final SecretKey KEY = (SecretKey)generateSecretKey();

    private static Key generateSecretKey() {
        try{
            KeyGenerator plzGifKey =  KeyGenerator.getInstance(ALGORITHM);
            plzGifKey.init(256);
            return plzGifKey.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.warn("The algorithm " + ALGORITHM + " is missing for some reason, and Java KeyGenerator documentation says it should not be.");
            ex.printStackTrace();
            return null;
        }
    }

    public String getToken(String userName) {
        String token = Jwts.builder()
            .subject(userName)
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(KEY)
            .compact();
        return token;
    }

    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token.replace(PREFIX, ""))
                .getPayload()
                .getSubject();
            if (user != null) {
                return user;
            }
        }
        return null;
    }
    
}
