package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.model.ServiceProvided;
import joelmaciel.service_control.domain.repository.ServiceProvidedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceProvidedService {

    private final ClientRegistrationService clientService;
    private final ServiceProvidedRepository serviceProvidedRepository;

    @Transactional
    public ServiceProvidedDTO save(ServiceProvidedRequestDTO serviceProvidedDTO) {

        Client client = clientService.searchById(serviceProvidedDTO.getClientId().getId());
        ServiceProvided serviceProvided = ServiceProvidedRequestDTO.toModel(serviceProvidedDTO)
                .toBuilder().client(client).build();

        return ServiceProvidedDTO.toDTO(serviceProvidedRepository.save(serviceProvided));
    }

    public List<ServiceProvidedDTO> findByNameClientAndMonth(String name, Integer month) {
        List<ServiceProvided> servicesProvided = serviceProvidedRepository.findByNameClientAndMonth("%" + name + "%", month);
        return servicesProvided.stream().map(serviceProvided -> ServiceProvidedDTO.toDTO(serviceProvided))
                .collect(Collectors.toList());

    }
}
