package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ClientRequestDTO {

    @NotBlank
    private String name;

    @CPF
    @NotNull
    private String cpf;

    public static Client toModel(ClientRequestDTO clientRequestDTO) {
        return Client.builder()
                .name(clientRequestDTO.getName())
                .cpf(clientRequestDTO.getCpf())
                .build();
    }

}
