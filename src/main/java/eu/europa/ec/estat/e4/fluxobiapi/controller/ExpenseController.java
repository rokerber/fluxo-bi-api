package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Expense;
import eu.europa.ec.estat.e4.fluxobiapi.domain.Expense;
import eu.europa.ec.estat.e4.fluxobiapi.dto.ExpenseRequestDTO;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ExpenseRepository;
import eu.europa.ec.estat.e4.fluxobiapi.service.ExpenseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        if (expenseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Expense existingExpense = expenseOptional.get();
        BeanUtils.copyProperties(expenseDetails, existingExpense);
        Expense updatedExpense = expenseRepository.save(existingExpense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
            Optional<Expense> expenseOptional = expenseRepository.findById(id);
            if (expenseOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            expenseRepository.delete(expenseOptional.get());
            return ResponseEntity.noContent().build();
    }
}