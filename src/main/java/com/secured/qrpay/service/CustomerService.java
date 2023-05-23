package com.secured.qrpay.service;

import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.response.BaseResponse;

public interface CustomerService {
    BaseResponse saveCustomerDetails(CustomerRequest customerRequest) throws AppException;

    BaseResponse getCustomerDetailsById(Long id) throws Exception;
}
