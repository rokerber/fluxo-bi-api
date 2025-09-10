package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RevenueResponseDTO(
    Long id,
    String description,
    BigDecimal amount,
    LocalDate revenueDate,
    ClientDTO client,
    ProductDTO product
) {
    public record ClientDTO(Long id, String name) {}
    public record ProductDTO(Long id, String name) {}
}