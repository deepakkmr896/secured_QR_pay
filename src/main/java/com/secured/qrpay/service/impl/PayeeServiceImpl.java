package com.secured.qrpay.service.impl;

import com.secured.qrpay.entity.Payee;
import com.secured.qrpay.enums.AppExceptionCode;
import com.secured.qrpay.enums.Status;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.factory.EncryptHandlerFactory;
import com.secured.qrpay.handler.EncryptionHandler;
import com.secured.qrpay.repository.PayeeRepository;
import com.secured.qrpay.request.PayeeRequest;
import com.secured.qrpay.response.BaseResponse;
import com.secured.qrpay.response.PayeeDetailsResponse;
import com.secured.qrpay.service.PayeeService;
import com.secured.qrpay.util.BasicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class PayeeServiceImpl implements PayeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayeeServiceImpl.class);
    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private EncryptHandlerFactory encryptHandlerFactory;

    @Override
    public BaseResponse savePayeeDetails(PayeeRequest payeeRequest) throws AppException {
        try {
            LOGGER.info("Getting to save the payee details!");
            Payee payee = new Payee();
            payee.setName(payeeRequest.getPayeeName());
            payee.setValidationTimeIntervalInHour(BasicUtil.isNull(payeeRequest.getSetValidationTimeIntervalInHour())
                    ? 24 : payeeRequest.getSetValidationTimeIntervalInHour());
            EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler("AES");
            payee.setPhoneNumber(encryptionHandler.encrypt(payeeRequest.getPhoneNumber()));
            payee.setEmail(encryptionHandler.encrypt(payeeRequest.getEmail()));
            payee = payeeRepository.save(payee);
            return BaseResponse.builder()
                    .message("Saved payee Successfully with id " + payee.getId())
                    .status(Status.SUCCESS)
                    .build();

        } catch (Exception e) {
            throw new AppException(AppExceptionCode.UNABLE_TO_SAVE_PAYEE.getErrorMsg());
        }
    }

    @Override
    public BaseResponse getPayeeDetailsById(Long id) throws Exception {
        try {
            LOGGER.info("Getting to fetch the payee details for id {}", id);
            Optional<Payee> payeeDetailsOptional = payeeRepository.findById(id);
            if (payeeDetailsOptional.isPresent()) {
                Payee payeeDetails = payeeDetailsOptional.get();
                EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler("AES");
                return PayeeDetailsResponse.builder()
                        .id(payeeDetails.getId())
                        .phoneNumber(encryptionHandler.decrypt(payeeDetails.getPhoneNumber()))
                        .email(encryptionHandler.decrypt(payeeDetails.getEmail()))
                        .name(payeeDetails.getName())
                        .isAuthenticated(payeeDetails.isAuthenticated())
                        .nextValidatedTime(payeeDetails.getNextValidatedTime())
                        .status(Status.SUCCESS)
                        .build();
            }
        } catch (Exception e) {
            throw new AppException("Not able to fetch the payee details for id " + id);
        }
        return null;
    }

    @Override
    public boolean validatePayee(String phoneNumber, Long payeeId) {
        if (BasicUtil.isEmpty(phoneNumber) || BasicUtil.isNull(payeeId)) {
            return false;
        }
        Optional<Payee> payeeOptional = payeeRepository.findById(payeeId);
        if (payeeOptional.isPresent()) {
            Payee payee = payeeOptional.get();
            if (payee.getPhoneNumber().equals(phoneNumber)) {
                payee.setAuthenticated(true);
                payee.setNextValidatedTime(LocalDateTime.now().plus(Duration.of(payee.getValidationTimeIntervalInHour(), ChronoUnit.HOURS)));
                payeeRepository.save(payee);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkValidity(Long payeeId) {
        if (BasicUtil.isNull(payeeId)) {
            return false;
        }
        Optional<Payee> payeeOptional = payeeRepository.findById(payeeId);
        if (payeeOptional.isPresent()) {
            Payee payee = payeeOptional.get();
            return payee.isAuthenticated() && payee.getNextValidatedTime().isAfter(LocalDateTime.now());
        }
        return false;
    }
}
