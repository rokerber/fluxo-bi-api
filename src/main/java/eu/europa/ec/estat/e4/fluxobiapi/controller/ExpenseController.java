// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/controller/ExpenseController.java
package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Expense;
import eu.europa.ec.estat.e4.fluxobiapi.dto.ExpenseRequestDTO;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ExpenseRepository;
import eu.europa.ec.estat.e4.fluxobiapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllOrderByDueDate();
    }

    @PostMapping
    public ResponseEntity<List<Expense>> createExpense(@RequestBody ExpenseRequestDTO dto) {
        List<Expense> savedExpenses = expenseService.createExpense(dto);
        return new ResponseEntity<>(savedExpenses, HttpStatus.CREATED);
    }
}