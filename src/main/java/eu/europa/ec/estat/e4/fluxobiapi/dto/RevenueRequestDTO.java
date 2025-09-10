// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/controller/dto/RevenueRequestDTO.java
package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

// Usar um record é uma forma moderna e concisa de criar DTOs imutáveis
public record RevenueRequestDTO(
        String description,
        BigDecimal amount,
        LocalDate revenueDate,
        Long clientId,
        Long productId
) {
}