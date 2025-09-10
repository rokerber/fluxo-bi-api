package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TimeSeriesDataPointDTO(
    LocalDate date,
    BigDecimal value
) {}