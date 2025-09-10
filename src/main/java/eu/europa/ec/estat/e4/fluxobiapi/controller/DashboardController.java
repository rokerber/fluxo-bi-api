package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.dto.DashboardSummaryDTO;
import eu.europa.ec.estat.e4.fluxobiapi.dto.TimeSeriesDataPointDTO;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ExpenseRepository;
import eu.europa.ec.estat.e4.fluxobiapi.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary() {
        BigDecimal totalRevenues = revenueRepository.findTotalRevenues();
        BigDecimal totalExpenses = expenseRepository.findTotalExpenses();
        BigDecimal balance = totalRevenues.subtract(totalExpenses);
        return new DashboardSummaryDTO(totalRevenues, totalExpenses, balance);
    }

    @GetMapping("/revenue-history")
    public List<TimeSeriesDataPointDTO> getRevenueHistory() {
        return revenueRepository.findRevenueTimeSeries();
    }

    @GetMapping("/expense-history")
    public List<TimeSeriesDataPointDTO> getExpenseHistory() {
        return expenseRepository.findExpenseTimeSeries();
    }
}