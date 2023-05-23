package com.secured.qrpay.service;

import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.request.PayeeRequest;
import com.secured.qrpay.response.BaseResponse;

public interface PayeeService {
    BaseResponse savePayeeDetails(PayeeRequest payeeRequest) throws AppException;

    BaseResponse getPayeeDetailsById(Long id) throws Exception;

    boolean validatePayee(String phoneNumber, Long payeeId);

    boolean checkValidity(Long payeeId);
}
