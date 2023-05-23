package com.secured.qrpay.exception.handler;

import com.secured.qrpay.enums.Status;
import com.secured.qrpay.exception.AppRunTimeException;
import com.secured.qrpay.response.BaseResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO - this handler will be good for internal server error, this bad request is the initial code, could be removed later
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BaseResponse baseResponse = BaseResponse.builder()
                .status(Status.FAILED)
                .message("Request is not valid! Please check your request!")
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleAppRuntimeException(AppRunTimeException e) {
        BaseResponse response = BaseResponse.builder()
                .message("Something Went Wrong!")
                .status(Status.FAILED)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
