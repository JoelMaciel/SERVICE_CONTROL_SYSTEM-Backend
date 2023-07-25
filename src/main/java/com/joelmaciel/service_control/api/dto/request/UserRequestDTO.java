package com.joelmaciel.service_control.api.dto.request;

import com.joelmaciel.service_control.domain.model.User;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank
    private String username;

    @CPF
    @NotBlank
    private String cpf;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public static User toModel(UserRequestDTO userRequestDTO) {
        return User.builder()
                .username(userRequestDTO.getUsername())
                .cpf(userRequestDTO.getCpf())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }

}
