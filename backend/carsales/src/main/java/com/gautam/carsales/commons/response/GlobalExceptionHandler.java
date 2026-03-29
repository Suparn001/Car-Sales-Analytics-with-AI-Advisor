package com.gautam.carsales.commons.response;

import com.gautam.carsales.dto.UploadSalesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<UploadSalesResponse>> handleAllExceptions(Exception ex) {
        UploadSalesResponse response = new UploadSalesResponse(0, 0, 0);
        ApiResponse<UploadSalesResponse> apiResponse = new ApiResponse<UploadSalesResponse>(false, ex.getMessage(), response, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ApiResponse<UploadSalesResponse>>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
