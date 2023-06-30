package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Getter
@Setter
public class ClientIdRequestDTO {

    @NotNull
    private Long id;

    public static Client toModel(ClientIdRequestDTO clientIdRequestDTO) {
        return Client.builder()
                .id(clientIdRequestDTO.getId())
                .build();
    }
}
