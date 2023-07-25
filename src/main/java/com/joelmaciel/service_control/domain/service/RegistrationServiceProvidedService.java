package com.joelmaciel.service_control.domain.service;

import com.joelmaciel.service_control.domain.model.Client;
import com.joelmaciel.service_control.domain.model.ServiceProvided;
import com.joelmaciel.service_control.domain.repository.ServiceProvidedRepository;
import com.joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import com.joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationServiceProvidedService {

    private final RegistrationClientService clientService;
    private final ServiceProvidedRepository serviceProvidedRepository;

    @Transactional
    public ServiceProvidedDTO save(ServiceProvidedRequestDTO serviceProvidedDTO) {

        Client client = clientService.searchById(serviceProvidedDTO.getClientId().getId());
        ServiceProvided serviceProvided = ServiceProvidedRequestDTO.toModel(serviceProvidedDTO)
                .toBuilder()
                .client(client)
                .build();

        return ServiceProvidedDTO.toDTO(serviceProvidedRepository.save(serviceProvided));
    }

    public List<ServiceProvidedDTO> findByNameClientAndMonth(String name, Integer month) {
        List<ServiceProvided> servicesProvided = serviceProvidedRepository.findByNameClientAndMonth("%" + name + "%", month);
        return servicesProvided.stream()
                .map(ServiceProvidedDTO::toDTO)
                .toList();

    }
}
