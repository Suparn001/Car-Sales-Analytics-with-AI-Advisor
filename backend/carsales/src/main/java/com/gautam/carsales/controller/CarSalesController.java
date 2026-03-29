package com.gautam.carsales.controller;

import com.gautam.carsales.commons.response.ApiResponse;
import com.gautam.carsales.dto.UploadSalesResponse;
import com.gautam.carsales.service.CarSalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/car-sales")
public class CarSalesController {
    private final CarSalesService carSalesService;

    public CarSalesController(CarSalesService carSalesService) {
        this.carSalesService = carSalesService;
    }

    private static ApiResponse<UploadSalesResponse> getApiResponse(UploadSalesResponse salesResponse) {
        String message = "";
        boolean success;
        if (salesResponse.getFailedCount() == 0) {
            message = "All records successfully uploaded";
            success = true;
        } else if (salesResponse.getSuccessCount() == 0) {
            message = "All records failed to upload";
            success = false;
        } else {
            message = "Uploaded with some error" + salesResponse.getFailedCount() + " row failed to upload";
            success = false;
        }
        return new ApiResponse<UploadSalesResponse>(success, message, salesResponse, HttpStatus.OK.value());
    }

    @PostMapping("/upload-file")
    public ResponseEntity<ApiResponse<UploadSalesResponse>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
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

        UploadSalesResponse uploadSalesResponse = carSalesService.uploadCsv(file);
        ApiResponse<UploadSalesResponse> apiResponse = getApiResponse(uploadSalesResponse);
        return ResponseEntity.ok(apiResponse);
    }
}
