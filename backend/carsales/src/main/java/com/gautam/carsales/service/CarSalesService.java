package com.gautam.carsales.service;

import com.gautam.carsales.dto.UploadSalesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CarSalesService {
    UploadSalesResponse uploadCsv(MultipartFile file) throws IOException;

}
