package com.secured.qrpay.handler.impl;

import com.secured.qrpay.constant.EncryptionConstants;
import com.secured.qrpay.handler.EncryptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESEncryptionHandler implements EncryptionHandler {
    @Value("${app.config.encryption.encrypt-key}")
    private String encryptKey;

    @Override
    public String encrypt(String plainText) {
        try {
            SecretKey secretKey = EncryptionHandler.digestAndFormatKey(encryptKey);
            Cipher cipher = Cipher.getInstance(EncryptionConstants.AES_ECB_PKCS5Padding_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            SecretKey secretKey = EncryptionHandler.digestAndFormatKey(encryptKey);
            Cipher cipher = Cipher.getInstance(EncryptionConstants.AES_ECB_PKCS5Padding_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public String getAlgorithm() {
        return "AES";
    }
}
