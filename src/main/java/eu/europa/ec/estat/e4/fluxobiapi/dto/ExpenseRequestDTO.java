package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequestDTO(
        String description,
        BigDecimal amount,
        LocalDate dueDate,
        Integer installments
) {
}