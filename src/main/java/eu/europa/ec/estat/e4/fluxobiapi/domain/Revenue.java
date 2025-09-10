// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/domain/Revenue.java
package eu.europa.ec.estat.e4.fluxobiapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "revenues")
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal amount; // Usar BigDecimal para valores financeiros

    @Column(nullable = false)
    private LocalDate revenueDate; // Data em que a receita foi registrada

    // --- Relacionamentos ---

    @ManyToOne(fetch = FetchType.LAZY) // Muitos registros de Receita podem estar associados a UM Cliente
    @JoinColumn(name = "client_id", nullable = false) // Chave estrangeira na tabela 'revenues'
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY) // Muitos registros de Receita podem estar associados a UM Produto
    @JoinColumn(name = "product_id", nullable = false) // Chave estrangeira na tabela 'revenues'
    private Product product;
}