package com.joelmaciel.service_control.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joelmaciel.service_control.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String cpf;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime creationDate;

    public static UserDTO toDTO(User user)  {
        return UserDTO.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .username(user.getUsername())
                .email(user.getEmail())
                .creationDate(user.getCreationDate())
                .build();
    }

}
