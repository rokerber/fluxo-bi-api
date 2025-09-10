package eu.europa.ec.estat.e4.fluxobiapi.controller;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Client;
import eu.europa.ec.estat.e4.fluxobiapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientRepository.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Client existingClient = clientOptional.get();
        existingClient.setName(clientDetails.getName());
        existingClient.setContactPerson(clientDetails.getContactPerson());
        existingClient.setEmail(clientDetails.getEmail());
        existingClient.setPhone(clientDetails.getPhone());
        Client updatedClient = clientRepository.save(existingClient);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.delete(clientOptional.get());
        return ResponseEntity.noContent().build();
    }
}