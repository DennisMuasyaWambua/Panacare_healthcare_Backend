package com.panacare.panabeans.shared;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PanaUtils {

    @Value("${app.version}")
    public String pomVersion;

    @Value("${app.secret}")
    public String appSecret;

    @Value("${app.secret.expiration.time}")
    public String appSecretExpirationTime;



    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getBaseUrl(HttpServletRequest req) {
        final String baseURL = req.getScheme() + "://" + req.getServerName()+ ":" + req.getServerPort() + req.getContextPath();
        logger.info("BaseUrl: {}", baseURL);
        return baseURL;
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


    public static String generateID(int length) {
        return generateRandomString(length);
    }

    public String generateID() {
        return UUID.randomUUID().toString();
    }

    private static String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        Random RANDOM = new SecureRandom();
        for (int i = 0; i < length; i++) {
            String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String maskEmail(String email) {
        String mask;
        String[] parts = email.split("@");
        if (parts[0].length() <= 3) {
            mask = parts[0].charAt(0) + "*****";
        } else {
            mask = parts[0].substring(0, 3) + "*****";
        }
        return mask + "@" + parts[1];
    }

    public String decodeFile(String base64Img, String path) {
        byte[] data = Base64.decodeBase64(base64Img);
        try (OutputStream stream = new FileOutputStream(path)) {
            stream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }


    public String generateEmailVerificationToken(String slug) {
        return Jwts.builder().setSubject(slug)
//                .setExpiration(new Date(System.currentTimeMillis() + appSecretExpirationTime))
                .signWith(SignatureAlgorithm.HS512, appSecret)
                .compact();
    }
}
