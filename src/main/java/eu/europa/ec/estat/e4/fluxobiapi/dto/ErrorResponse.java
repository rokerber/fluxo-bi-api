// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/controller/dto/ErrorResponse.java
package eu.europa.ec.estat.e4.fluxobiapi.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}