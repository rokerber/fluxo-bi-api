package eu.europa.ec.estat.e4.fluxobiapi.repository;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Expense;
import eu.europa.ec.estat.e4.fluxobiapi.dto.TimeSeriesDataPointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    BigDecimal findTotalExpenses();

    @Query("SELECT new eu.europa.ec.estat.e4.fluxobiapi.dto.TimeSeriesDataPointDTO(e.dueDate, SUM(e.amount)) " +
            "FROM Expense e GROUP BY e.dueDate ORDER BY e.dueDate ASC")
    List<TimeSeriesDataPointDTO> findExpenseTimeSeries();

    @Query("SELECT e FROM Expense e ORDER BY e.dueDate ASC")
    List<Expense> findAllOrderByDueDate();
}