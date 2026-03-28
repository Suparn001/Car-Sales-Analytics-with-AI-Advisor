package com.gautam.carsales.controller;

import com.gautam.carsales.commons.response.ApiResponse;
import com.gautam.carsales.dto.UploadSalesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/car-sales")
public class CarSalesController {

    @PostMapping("/upload-file")
    public ResponseEntity<ApiResponse<UploadSalesResponse>> uploadFile(@RequestParam("file") MultipartFile file) {
        // validation-> file is available or not
        if (file.isEmpty()) {
            // reponse
            UploadSalesResponse uploadSalesResponse = new UploadSalesResponse(
                    0, 0, 0);
            ApiResponse<UploadSalesResponse> apiRepsonse = new ApiResponse<>(false,
                    "File is Empty",
                    uploadSalesResponse,
                    HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<ApiResponse<UploadSalesResponse>>(apiRepsonse, HttpStatus.BAD_REQUEST);
        }
        // response
        return null;
    }
}
