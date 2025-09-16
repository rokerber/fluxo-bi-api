// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/controller/ProductController.java
package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Product;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(201).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = productOptional.get();
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());

        Product updatedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {  // Mudei para <?>
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            productRepository.delete(productOptional.get());
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "REFERENTIAL_INTEGRITY_VIOLATION",
                            "message", "Este produto não pode ser excluído pois está vinculado a receitas ou despesas.",
                            "suggestion", "Remova primeiro todas as receitas/despesas associadas a este produto."
                    ));
        }
    }
}