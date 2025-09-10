// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/controller/RevenueController.java
package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Client;
import eu.europa.ec.estat.e4.fluxobiapi.domain.Product;
import eu.europa.ec.estat.e4.fluxobiapi.domain.Revenue;
import eu.europa.ec.estat.e4.fluxobiapi.dto.RevenueRequestDTO;
import eu.europa.ec.estat.e4.fluxobiapi.dto.RevenueResponseDTO;
import eu.europa.ec.estat.e4.fluxobiapi.mapper.RevenueMapper;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ClientRepository;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ProductRepository;
import eu.europa.ec.estat.e4.fluxobiapi.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/revenues")
public class RevenueController {

    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Revenue> createRevenue(@RequestBody RevenueRequestDTO dto) {
        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + dto.clientId()));
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.productId()));
        Revenue newRevenue = new Revenue();
        newRevenue.setDescription(dto.description());
        newRevenue.setAmount(dto.amount());
        newRevenue.setRevenueDate(dto.revenueDate());
        newRevenue.setClient(client);
        newRevenue.setProduct(product);

        Revenue savedRevenue = revenueRepository.save(newRevenue);
        return ResponseEntity.status(201).body(savedRevenue);
    }

    @GetMapping
    public List<RevenueResponseDTO> getAllRevenues() {
        return revenueRepository.findAll()
                .stream()
                .map(RevenueMapper::toDto)
                .collect(Collectors.toList());
    }
}