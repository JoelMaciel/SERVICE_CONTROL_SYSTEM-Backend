package joelmaciel.service_control.api.dto.request;

import joelmaciel.service_control.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public static User toModel(UserRequestDTO userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
    }
}
