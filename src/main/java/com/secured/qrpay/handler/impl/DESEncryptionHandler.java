package com.secured.qrpay.handler.impl;

import com.secured.qrpay.handler.EncryptionHandler;
import org.springframework.stereotype.Component;

@Component
public class DESEncryptionHandler implements EncryptionHandler {
    @Override
    public String encrypt(String plainText) {
        return null;
    }

    @Override
    public String decrypt(String encryptedText) {
        return null;
    }

    @Override
    public String getAlgorithm() {
        return "DES";
    }
}
