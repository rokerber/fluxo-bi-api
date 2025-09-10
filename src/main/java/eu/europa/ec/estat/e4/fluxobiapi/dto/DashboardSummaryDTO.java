package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.math.BigDecimal;

public record DashboardSummaryDTO(
    BigDecimal totalRevenues,
    BigDecimal totalExpenses,
    BigDecimal balance
) {}