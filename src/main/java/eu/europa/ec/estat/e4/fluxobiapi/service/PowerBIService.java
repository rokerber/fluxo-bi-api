package eu.europa.ec.estat.e4.fluxobiapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI; // <-- Adicione esta importação
import java.net.URISyntaxException; // <-- Adicione esta importação
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PowerBIService {

    private static final Logger logger = LoggerFactory.getLogger(PowerBIService.class);

    @Value("${powerbi.api.url:}")
    private String powerBiApiUrl;

    @Value("${powerbi.enabled:false}")
    private boolean powerBiEnabled;

    private final RestTemplate restTemplate;

    public PowerBIService() {
        this.restTemplate = new RestTemplate();
    }


    public void sendRevenueData(Double value, LocalDateTime date) {
        if (!powerBiEnabled || powerBiApiUrl.isEmpty()) {
            logger.info("Power BI integration disabled or URL not configured");
            return;
        }

        try {
            Map<String, Object> data = Map.of(
                    "date", date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z",
                    "value", value
            );

            List<Map<String, Object>> payload = List.of(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(payload, headers);

            // ----- ALTERAÇÃO AQUI -----
            // Crie um objeto URI para evitar que o RestTemplate altere a codificação da URL.
            URI powerBiUri = new URI(powerBiApiUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(powerBiUri, request, String.class);
            // --------------------------

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Revenue data sent successfully to Power BI: {}", value);
            } else {
                logger.warn("Failed to send data to Power BI. Status: {}", response.getStatusCode());
            }

        } catch (URISyntaxException e) {
            logger.error("Error: The Power BI URL is malformed", e);
        } catch (Exception e) {
            logger.error("Error sending data to Power BI", e);
        }
    }
}