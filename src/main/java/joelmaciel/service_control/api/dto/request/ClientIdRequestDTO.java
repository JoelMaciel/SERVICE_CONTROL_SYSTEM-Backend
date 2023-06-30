package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientIdRequestDTO {

    private Long id;

    public static Client toModel(ClientIdRequestDTO clientIdRequestDTO) {
        return Client.builder()
                .id(clientIdRequestDTO.getId())
                .build();
    }
}
