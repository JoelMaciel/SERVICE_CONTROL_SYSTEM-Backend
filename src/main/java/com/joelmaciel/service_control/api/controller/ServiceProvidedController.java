package com.joelmaciel.service_control.api.controller;

import com.joelmaciel.service_control.api.controller.openapi.ServiceProvideControllerOpenApi;
import com.joelmaciel.service_control.domain.service.RegistrationServiceProvidedService;
import com.joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import com.joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/api/services-provided")
public class ServiceProvidedController  implements ServiceProvideControllerOpenApi {

    private final RegistrationServiceProvidedService registrationServiceProvidedService;

    @GetMapping
    public List<ServiceProvidedDTO> getAll(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                        @RequestParam(value = "month", required = false) Integer month) {
        return registrationServiceProvidedService.findByNameClientAndMonth(name, month);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceProvidedDTO save(@RequestBody @Valid ServiceProvidedRequestDTO serviceProvidedRequestDTO) {
        return registrationServiceProvidedService.save(serviceProvidedRequestDTO);
    }
}
