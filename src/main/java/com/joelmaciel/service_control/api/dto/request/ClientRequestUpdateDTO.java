package com.joelmaciel.service_control.api.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestUpdateDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;


}
