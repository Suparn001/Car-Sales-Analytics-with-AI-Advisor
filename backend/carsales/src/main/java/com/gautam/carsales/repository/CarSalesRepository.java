package com.gautam.carsales.repository;

import com.gautam.carsales.dto.YearlyCountDto;
import com.gautam.carsales.entity.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales,Long>{
    boolean existsByCarNumber(String carNumber);

    @Query("""
            Select new com.gautam.carsales.dto.YearlyCountDto(c.year,count(c))
            from CarSales c
            Group by c.year
            Order by c.year
            """)
    List<YearlyCountDto> getYearlyCount();
}
