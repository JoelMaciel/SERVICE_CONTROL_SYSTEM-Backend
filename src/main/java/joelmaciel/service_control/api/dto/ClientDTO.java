package joelmaciel.service_control.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String username;
    private String cpf;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime updateDate;

    public static ClientDTO toDTO(Client client)  {
        return ClientDTO.builder()
                .id(client.getId())
                .cpf(client.getCpf())
                .username(client.getUsername())
                .email(client.getEmail())
                .creationDate(client.getCreationDate())
                .updateDate(client.getUpdateDate())
                .build();
    }

}
