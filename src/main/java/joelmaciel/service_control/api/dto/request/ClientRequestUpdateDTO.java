package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestUpdateDTO {

    @NotBlank
    private String username;


    @NotBlank
    @Email
    private String email;


    public static Client toModel(ClientRequestUpdateDTO clientRequestDTO) {
        return Client.builder()
                .username(clientRequestDTO.getUsername())
                .email(clientRequestDTO.getEmail())
                .build();
    }

}
