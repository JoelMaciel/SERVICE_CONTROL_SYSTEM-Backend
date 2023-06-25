package joelmaciel.service_control.api.dto;

import joelmaciel.service_control.domain.model.Client;
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
    private OffsetDateTime creationDate;
    private OffsetDateTime updateDate;

    public static ClientDTO toDTO(Client client)  {
        return ClientDTO.builder()
                .id(client.getId())
                .cpf(client.getCpf())
                .name(client.getName())
                .creationDate(client.getCreationDate())
                .updateDate(client.getUpdateDate())
                .build();
    }

}
