package joelmaciel.service_control.api.security.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtDTO {

    @NonNull
    private String token;

    private String type = "Bearer";
}
