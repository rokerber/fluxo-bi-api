// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/domain/Expense.java
package eu.europa.ec.estat.e4.fluxobiapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean paid;
}