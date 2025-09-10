// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/repository/ProductRepository.java
package eu.europa.ec.estat.e4.fluxobiapi.repository;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}