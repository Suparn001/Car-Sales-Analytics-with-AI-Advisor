package com.gautam.carsales.repository;

import com.gautam.carsales.entity.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales,Long>{
    boolean existsByCarNumber(String carNumber);
}
