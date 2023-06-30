package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.model.ServiceProvided;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ServiceProvidedRequestDTO {

    private String description;
    private BigDecimal price;
    private OffsetDateTime creationDate;
    private ClientIdRequestDTO clientId;

    public static ServiceProvided toModel(ServiceProvidedRequestDTO serviceProvidedDTO) {
        return ServiceProvided.builder()
                .description(serviceProvidedDTO.getDescription())
                .price(serviceProvidedDTO.getPrice())
                .client(ClientIdRequestDTO.toModel(serviceProvidedDTO.getClientId()))
                .build();
    }
}
