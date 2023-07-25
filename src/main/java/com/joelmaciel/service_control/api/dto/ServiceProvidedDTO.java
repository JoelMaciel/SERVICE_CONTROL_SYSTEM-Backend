package com.joelmaciel.service_control.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joelmaciel.service_control.domain.model.ServiceProvided;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ServiceProvidedDTO {

    private Long id;
    private String description;
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime creationDate;
    private ClientDTO client;

    public static ServiceProvidedDTO toDTO(ServiceProvided serviceProvided) {
        return ServiceProvidedDTO.builder()
                .id(serviceProvided.getId())
                .description(serviceProvided.getDescription())
                .price(serviceProvided.getPrice())
                .creationDate(serviceProvided.getCreationDate())
                .client(ClientDTO.toDTO(serviceProvided.getClient()))
                .build();

    }
}
