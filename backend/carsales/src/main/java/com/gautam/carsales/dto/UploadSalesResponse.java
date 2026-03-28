package com.gautam.carsales.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// generally in dtos setter is not used to make it immutable
public class UploadSalesResponse {
    private int totalRecords;
    private int successCount;
    private int failedCount;


}
