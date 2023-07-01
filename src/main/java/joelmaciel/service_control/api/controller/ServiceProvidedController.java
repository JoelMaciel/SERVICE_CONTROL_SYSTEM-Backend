package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.controller.openapi.ServiceProvideControllerOpenApi;
import joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import joelmaciel.service_control.domain.service.RegistrationServiceProvidedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
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
