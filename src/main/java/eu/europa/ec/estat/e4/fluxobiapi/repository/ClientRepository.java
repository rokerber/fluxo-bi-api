package eu.europa.ec.estat.e4.fluxobiapi.repository;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}