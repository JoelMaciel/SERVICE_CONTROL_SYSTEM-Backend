package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
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
    private String username;

    @CPF
    @NotNull
    private String cpf;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public static Client toModel(ClientRequestDTO clientRequestDTO) {
        return Client.builder()
                .username(clientRequestDTO.getUsername())
                .cpf(clientRequestDTO.getCpf())
                .email(clientRequestDTO.getEmail())
                .password(clientRequestDTO.getPassword())
                .build();
    }

}
