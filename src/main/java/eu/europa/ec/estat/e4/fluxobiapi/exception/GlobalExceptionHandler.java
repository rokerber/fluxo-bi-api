// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/exception/GlobalExceptionHandler.java
package eu.europa.ec.estat.e4.fluxobiapi.exception;

import eu.europa.ec.estat.e4.fluxobiapi.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String friendlyMessage = "Já existe um registro com os dados informados. Por favor, verifique um novo valor.";
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Violação de Integridade de Dados",
                friendlyMessage
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}