package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import joelmaciel.service_control.domain.model.ServiceProvided;
import joelmaciel.service_control.domain.service.ServiceProvidedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/services-provided")
public class ServiceProvidedController {

    private final ServiceProvidedService serviceProvidedService;

    @GetMapping
    public List<ServiceProvidedDTO> getAll(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                        @RequestParam(value = "month", required = false) Integer month) {
        return serviceProvidedService.findByNameClientAndMonth(name, month);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceProvidedDTO save(@RequestBody ServiceProvidedRequestDTO serviceProvidedRequestDTO) {
        return serviceProvidedService.save(serviceProvidedRequestDTO);
    }
}
