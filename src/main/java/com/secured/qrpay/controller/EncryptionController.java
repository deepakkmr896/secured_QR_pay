package com.secured.qrpay.controller;

import com.secured.qrpay.factory.EncryptHandlerFactory;
import com.secured.qrpay.handler.EncryptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {
    @Autowired
    private EncryptHandlerFactory encryptHandlerFactory;

    @GetMapping(value = "/encrypt")
    public ResponseEntity<String> getEncryptedData(@RequestParam("value") String valueToEncrypt, @RequestParam("algo") String algorithm) {
        EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler(algorithm);
        String encryptedData = encryptionHandler.encrypt(valueToEncrypt);
        return new ResponseEntity<>(encryptedData, HttpStatus.OK);
    }

    @GetMapping(value = "/decrypt")
    public ResponseEntity<String> getDecryptedData(@RequestParam("value") String valueToDecrypt, @RequestParam("algo") String algorithm) {
        EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler(algorithm);
        String encryptedData = encryptionHandler.decrypt(valueToDecrypt);
        return new ResponseEntity<>(encryptedData, HttpStatus.OK);
    }
}
