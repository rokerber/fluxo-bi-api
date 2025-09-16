package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Client;
import eu.europa.ec.estat.e4.fluxobiapi.domain.Revenue;
import eu.europa.ec.estat.e4.fluxobiapi.domain.Product;
import eu.europa.ec.estat.e4.fluxobiapi.dto.RevenueRequestDTO;
import eu.europa.ec.estat.e4.fluxobiapi.dto.RevenueResponseDTO;
import eu.europa.ec.estat.e4.fluxobiapi.mapper.RevenueMapper;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ClientRepository;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ProductRepository;
import eu.europa.ec.estat.e4.fluxobiapi.repository.RevenueRepository;
import eu.europa.ec.estat.e4.fluxobiapi.service.PowerBIService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private PowerBIService powerBIService;

    @PostMapping
    public ResponseEntity<Revenue> createRevenue(@RequestBody RevenueRequestDTO dto) {
        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + dto.clientId()));
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.productId()));

        Revenue newRevenue = new Revenue();
        newRevenue.setDescription(dto.description());
        newRevenue.setAmount(dto.amount());
        newRevenue.setRevenueDate(dto.revenueDate() != null ? dto.revenueDate() : LocalDate.now());
        newRevenue.setClient(client);
        newRevenue.setProduct(product);

        Revenue savedRevenue = revenueRepository.save(newRevenue);

        try {
            powerBIService.sendRevenueData(
                    savedRevenue.getAmount().doubleValue(),
                    savedRevenue.getRevenueDate().atStartOfDay()  // Converter para LocalDateTime
            );
        } catch (Exception e) {
            // Log do erro mas não falha a operação principal
            System.err.println("Erro ao enviar para Power BI: " + e.getMessage());
        }

        return ResponseEntity.status(201).body(savedRevenue);
    }

    @GetMapping
    public List<RevenueResponseDTO> getAllRevenues() {
        return revenueRepository.findAll()
                .stream()
                .map(RevenueMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenue> getRevenueById(@PathVariable Long id) {
        return revenueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revenue> updateRevenue(@PathVariable Long id, @RequestBody Revenue revenueDetails) {
        Optional<Revenue> revenueOptional = revenueRepository.findById(id);
        if (revenueOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Revenue existingRevenue = revenueOptional.get();
        BeanUtils.copyProperties(revenueDetails, existingRevenue);
        Revenue updatedRevenue = revenueRepository.save(existingRevenue);
        return ResponseEntity.ok(updatedRevenue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRevenue(@PathVariable Long id) {
        Optional<Revenue> revenueOptional = revenueRepository.findById(id);
        if (revenueOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        revenueRepository.delete(revenueOptional.get());
        return ResponseEntity.noContent().build();
    }
}