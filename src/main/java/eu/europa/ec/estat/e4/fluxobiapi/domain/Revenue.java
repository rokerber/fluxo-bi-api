// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/domain/Revenue.java
package eu.europa.ec.estat.e4.fluxobiapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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


    @ManyToOne(fetch = FetchType.LAZY) // Muitos registros de Receita podem estar associados a UM Cliente
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude // Chave estrangeira na tabela 'revenues'
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY) // Muitos registros de Receita podem estar associados a UM Produto
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude // Chave estrangeira na tabela 'revenues'
    private Product product;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Revenue revenue = (Revenue) o;
        return getId() != null && Objects.equals(getId(), revenue.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}