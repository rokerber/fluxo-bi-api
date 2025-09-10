package eu.europa.ec.estat.e4.fluxobiapi.repository;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Revenue;
import eu.europa.ec.estat.e4.fluxobiapi.dto.TimeSeriesDataPointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Revenue r")
    BigDecimal findTotalRevenues();

    @Query("SELECT new eu.europa.ec.estat.e4.fluxobiapi.dto.TimeSeriesDataPointDTO(r.revenueDate, SUM(r.amount)) " +
            "FROM Revenue r GROUP BY r.revenueDate ORDER BY r.revenueDate ASC")
    List<TimeSeriesDataPointDTO> findRevenueTimeSeries();
}