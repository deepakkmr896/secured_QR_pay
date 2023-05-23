package com.secured.qrpay.handler;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public interface EncryptionHandler {
    String encrypt(String plainText);
    String decrypt(String encryptedText);
    String getAlgorithm();

    static SecretKey digestAndFormatKey(String key) {
        try {
            byte[] digestedKey = key.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            digestedKey = messageDigest.digest(digestedKey);
            digestedKey = Arrays.copyOf(digestedKey, 16);
            return new SecretKeySpec(digestedKey, "AES");
        } catch (UnsupportedEncodingException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        return null;
    }
}
