package com.secured.qrpay.controller;

import com.secured.qrpay.enums.Status;
import com.secured.qrpay.request.PaymentRequest;
import com.secured.qrpay.response.BaseResponse;
import com.secured.qrpay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/transact")
    public ResponseEntity<BaseResponse> transactPayment(@RequestBody PaymentRequest transactionRequest) {
        try {
            BaseResponse response = paymentService.initiatePayment(transactionRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).status(Status.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
