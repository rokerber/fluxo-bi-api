// src/main/java/eu/europa/ec/estat/e4/fluxobiapi/mapper/RevenueMapper.java
package eu.europa.ec.estat.e4.fluxobiapi.mapper;

import eu.europa.ec.estat.e4.fluxobiapi.domain.Revenue;
import eu.europa.ec.estat.e4.fluxobiapi.dto.RevenueResponseDTO;

public class RevenueMapper {

    public static RevenueResponseDTO toDto(Revenue revenue) {
        RevenueResponseDTO.ClientDTO clientDTO = new RevenueResponseDTO.ClientDTO(
                revenue.getClient().getId(),
                revenue.getClient().getName()
        );

        RevenueResponseDTO.ProductDTO productDTO = new RevenueResponseDTO.ProductDTO(
                revenue.getProduct().getId(),
                revenue.getProduct().getName()
        );

        return new RevenueResponseDTO(
                revenue.getId(),
                revenue.getDescription(),
                revenue.getAmount(),
                revenue.getRevenueDate(),
                clientDTO,
                productDTO
        );
    }
}