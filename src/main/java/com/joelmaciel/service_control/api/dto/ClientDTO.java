package com.joelmaciel.service_control.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joelmaciel.service_control.domain.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private OffsetDateTime creationDate;

    public static ClientDTO toDTO(Client client)  {
        return ClientDTO.builder()
                .id(client.getId())
                .cpf(client.getCpf())
                .name(client.getName())
                .email(client.getEmail())
                .creationDate(client.getCreationDate())
                .build();
    }

}
