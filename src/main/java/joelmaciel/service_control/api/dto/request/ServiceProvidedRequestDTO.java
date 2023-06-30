package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.ServiceProvided;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ServiceProvidedRequestDTO {

    @NotBlank
    private String description;

    @PositiveOrZero
    @NotNull
    private BigDecimal price;

    private OffsetDateTime creationDate;

    @Valid
    @NotNull
    private ClientIdRequestDTO clientId;

    public static ServiceProvided toModel(ServiceProvidedRequestDTO serviceProvidedDTO) {
        return ServiceProvided.builder()
                .description(serviceProvidedDTO.getDescription())
                .price(serviceProvidedDTO.getPrice())
                .client(ClientIdRequestDTO.toModel(serviceProvidedDTO.getClientId()))
                .build();
    }
}
