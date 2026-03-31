package com.gautam.carsales.service;

import com.gautam.carsales.dto.UploadSalesResponse;
import com.gautam.carsales.dto.YearlyCountDto;
import com.gautam.carsales.entity.CarSales;
import com.gautam.carsales.repository.CarSalesRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarSalesServiceImpl implements CarSalesService {
    private final CarSalesRepository carSalesRepository;

    public CarSalesServiceImpl(CarSalesRepository carSalesRepository) {
        this.carSalesRepository = carSalesRepository;
    }


    @Override
    public UploadSalesResponse uploadCsv(MultipartFile file) throws IOException {
        List<CarSales> carSalesList = new ArrayList<CarSales>();

        int failCount = 0;
        int totalCount = 0;
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        ) {
// csv format
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader() // header
                    .setSkipHeaderRecord(true) // skip (not treated as data)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            CSVParser csvParser = CSVParser.parse(bufferedReader, csvFormat);

            for (CSVRecord record : csvParser) {
                totalCount++;
                try {

                    String carNumber = record.get("Car Number");
                    boolean exists = carSalesRepository.existsByCarNumber(carNumber);
                    if (exists) {
                        failCount++;
                        System.out.println("Car number " + carNumber + " already exists");
                        continue;
                    }
                    CarSales carSales = new CarSales();

// String
                    carSales.setCarNumber(record.get("Car Number"));
                    carSales.setBrand(record.get("Brand"));
                    carSales.setModel(record.get("Model"));
                    carSales.setColor(record.get("Color"));

// Integer
                    carSales.setYear(Integer.parseInt(record.get("Year")));

// Date (dd-MM-yyyy)
                    carSales.setDateOfPurchase(
                            LocalDate.parse(
                                    record.get("Date of Purchase"),
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
                            )
                    );

// Time (HH:mm:ss or HH:mm)
                    carSales.setTimeOfPurchase(
                            LocalTime.parse(record.get("Time of Purchase"))
                    );

// Long
                    carSales.setPrice(Long.parseLong(record.get("Price (Rs)")));

// Double
                    carSales.setMileage(Double.parseDouble(record.get("Mileage (km/l)")));

// Integer
                    carSales.setEngine(Integer.parseInt(record.get("Engine (cc)")));

// String
                    carSales.setFuelType(record.get("Fuel Type"));
                    carSales.setPaymentMode(record.get("Payment Mode"));
                    carSales.setState(record.get("State"));
                    carSales.setCity(record.get("City"));
                    carSales.setCustomerName(record.get("Customer Name"));
                    carSales.setContactNumber(record.get("Contact Number"));
                    carSales.setEmail(record.get("Email"));

// Integer
                    carSales.setWarrantyPeriod(
                            Integer.parseInt(record.get("Warranty Period (years)"))
                    );

                    carSalesList.add(carSales);
                } catch (Exception e) {
                    failCount++;
                    throw new RemoteException("Fail to process row:" + e.getMessage());
                }
            }
            if (!carSalesList.isEmpty()) {
                carSalesRepository.saveAll(carSalesList);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse csv" + e.getMessage());
        }

        return new UploadSalesResponse(totalCount, totalCount - failCount, failCount);
    }

    @Override
    public List<YearlyCountDto> getYearlyCount() {
        return carSalesRepository.getYearlyCount();
    }
}
