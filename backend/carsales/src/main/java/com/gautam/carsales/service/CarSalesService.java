package com.gautam.carsales.service;

import com.gautam.carsales.dto.UploadSalesResponse;
import com.gautam.carsales.dto.YearlyCountDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CarSalesService {
    UploadSalesResponse uploadCsv(MultipartFile file) throws IOException;

    List<YearlyCountDto> getYearlyCount();
}
