package com.secured.qrpay.controller;


import com.secured.qrpay.enums.Status;
import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.response.BaseResponse;
import com.secured.qrpay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/save")
    public ResponseEntity<BaseResponse> saveCustomerDetails(@RequestBody CustomerRequest customerRequest) {
        try {
            BaseResponse response = customerService.saveCustomerDetails(customerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).status(Status.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<BaseResponse> getCustomerDetails(@PathVariable("id") Long id) {
        try {
            BaseResponse customerDetailsResponse = customerService.getCustomerDetailsById(id);
            return new ResponseEntity<>(customerDetailsResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.builder().message(e.getMessage()).status(Status.FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
