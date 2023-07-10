package joelmaciel.service_control.api.dto;

import joelmaciel.service_control.domain.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;

    public static ClientDTO toDTO(Client client)  {
        return ClientDTO.builder()
                .id(client.getId())
                .cpf(client.getCpf())
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }

}
