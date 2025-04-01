package com.panacare.panabeans.shared;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Converter
public class DatabaseAESConverter implements AttributeConverter<String, String> {

    private static final String AES = "AES";

    private static final String SECRET = "secretkey";

    private final  SecretKeySpec secretKey;

    public DatabaseAESConverter()throws Exception {
        MessageDigest sha = null;
        byte[] key;
        key = SECRET.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        this.secretKey = new SecretKeySpec(key, AES);
//        String hexSecretKey = secretKeyToHexString(this.secretKey);
//        log.info("The generated secret key is {}", hexSecretKey );
    }

    @Override
    public String convertToDatabaseColumn(String value) {
        if(!StringUtils.isEmpty(value))
            try {
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
            } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
                    | NoSuchPaddingException | NoSuchProviderException e) {
                throw new IllegalStateException(e);
            }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String value) {
        if(!StringUtils.isEmpty(value))
            try {
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
                cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            }
        return null;
    }

    public String secretKeyToHexString(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        StringBuilder hexString = new StringBuilder();
        for (byte keyByte : keyBytes) {
            hexString.append(String.format("%02X", keyByte));
        }
        return hexString.toString();
    }
}
