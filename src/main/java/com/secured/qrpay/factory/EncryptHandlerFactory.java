package com.secured.qrpay.factory;

import com.secured.qrpay.handler.EncryptionHandler;
import com.secured.qrpay.handler.impl.AESEncryptionHandler;
import com.secured.qrpay.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EncryptHandlerFactory {
    @Autowired
    private Set<EncryptionHandler> encryptionHandlers;

    public EncryptionHandler getHandler(String algo) {
        if (BasicUtil.isEmpty(algo)) {
            return new AESEncryptionHandler();
        }
        return encryptionHandlers.stream()
                .filter(eh -> eh.getAlgorithm().equals(algo))
                .findFirst().orElse(new AESEncryptionHandler());
    }
}
