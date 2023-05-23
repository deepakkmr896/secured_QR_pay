package com.secured.qrpay.controller;

import com.secured.qrpay.enums.Status;
import com.secured.qrpay.request.PayeeRequest;
import com.secured.qrpay.response.BaseResponse;
import com.secured.qrpay.service.PayeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payee")
public class PayeeController {

    @Autowired
    private PayeeService payeeService;

    @PostMapping(value = "/save")
    public ResponseEntity<BaseResponse> savePayeeDetails(@RequestBody PayeeRequest payeeRequest) {
        try {
            BaseResponse response = payeeService.savePayeeDetails(payeeRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).status(Status.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<BaseResponse> getPayeeDetails(@PathVariable("id") Long id) {
        try {
            BaseResponse payeeDetailsResponse = payeeService.getPayeeDetailsById(id);
            return new ResponseEntity<>(payeeDetailsResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).status(Status.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Boolean> validatePayee(@RequestParam String phoneNumber, @RequestParam Long payeeId) {
        try {
            boolean isValidated = payeeService.validatePayee(phoneNumber, payeeId);
            return new ResponseEntity<>(isValidated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/check/validity")
    public ResponseEntity<Boolean> checkValidity(@RequestParam Long payeeId) {
        boolean isValid = false;
        try {
            isValid = payeeService.checkValidity(payeeId);
            return new ResponseEntity<>(isValid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
