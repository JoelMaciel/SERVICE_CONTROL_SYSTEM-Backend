package com.joelmaciel.service_control;

import com.joelmaciel.service_control.api.dto.request.UserRequestDTO;
import com.joelmaciel.service_control.domain.exception.UserNotFoundException;
import com.joelmaciel.service_control.domain.model.User;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import com.joelmaciel.service_control.domain.service.RegistrationUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegistrationUserTest {

    @Autowired
    private RegistrationUserService registrationUserService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSucceedWhenFetchingUserById() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("JoelViana")
                .cpf("28816044047")
                .email("joel023@bol.com")
                .password("12345678")
                .build();
        User user = UserRequestDTO.toModel(userRequestDTO);
        userRepository.save(user);
        Long userId = user.getId();

        User foundClient = userRepository.findById(userId).orElse(null);

        Assertions.assertNotNull(foundClient);
        Assertions.assertEquals("JoelViana", foundClient.getUsername());
        Assertions.assertEquals("28816044047", foundClient.getCpf());
        Assertions.assertEquals("joel023@bol.com", foundClient.getEmail());
    }


    @Test
    public void shouldSuccessfullyRegisterUser() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("AlexMario")
                .cpf("39669067081")
                .email("alexma@gmail.com")
                .password("12345678")
                .build();
        User user = userRepository.save(UserRequestDTO.toModel(userRequestDTO));

        assertThat(userRequestDTO).isNotNull();
        assertThat(user.getCpf()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
        assertThat(user.getUsername()).isNotNull();
    }

    @Test
    public void shouldFailToSaveUserWithoutUsernameAndCPF() {
        UserRequestDTO user = UserRequestDTO.builder()
                .username(null)
                .cpf(null)
                .email("teste@boll.com")
                .password("12345678")
                .build();

        DataIntegrityViolationException expectedError = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> {
                    registrationUserService.saveUser(user);
                });
        assertThat(expectedError).isNotNull();
    }

    @Test
    public void shouldFailedToDeleteAUserThatDoesNotExist() {
        UserNotFoundException expectedError = Assertions.assertThrows(UserNotFoundException.class,
                () -> {
                    registrationUserService.removeUser(10L);
                });

        assertThat(expectedError).isNotNull();
    }


}
