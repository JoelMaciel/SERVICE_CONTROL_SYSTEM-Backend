package com.joelmaciel.service_control.api.dto.request;

import com.joelmaciel.service_control.domain.model.Client;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {

    @NotBlank
    private String name;

    @CPF
    @NotNull
    private String cpf;

    @NotBlank
    @Email
    private String email;

    public static Client toModel(ClientRequestDTO clientRequestDTO) {
        return Client.builder()
                .name(clientRequestDTO.getName())
                .cpf(clientRequestDTO.getCpf())
                .email(clientRequestDTO.getEmail())
                .build();
    }

}
