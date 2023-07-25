package com.joelmaciel.service_control.util;

import com.joelmaciel.service_control.api.dto.UserDTO;
import com.joelmaciel.service_control.api.dto.request.UserRequestDTO;
import com.joelmaciel.service_control.api.security.dto.LoginDTO;
import com.joelmaciel.service_control.domain.enums.RoleType;
import com.joelmaciel.service_control.domain.model.Role;
import com.joelmaciel.service_control.domain.model.User;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import com.joelmaciel.service_control.domain.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class LoginData {
    private RoleService roleService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public LoginData(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initializeRoles() {
        Role roleAdmin = Role.builder()
                .roleId(1L)
                .roleName(RoleType.ROLE_ADMIN)
                .build();

        Role roleUser = Role.builder()
                .roleId(2L)
                .roleName(RoleType.ROLE_USER)
                .build();

        roleService.save(roleAdmin);
        roleService.save(roleUser);
    }

    public UserDTO createUserAdmin() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("JoelMaciel")
                .cpf("83626640027")
                .email("teste@teste.com")
                .password("12345678")
                .build();

        Role role = roleService.findByRoleName(RoleType.ROLE_ADMIN);

        User user = UserRequestDTO.toModel(userRequestDTO).toBuilder()
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        User savedUser = userRepository.save(user);
        return UserDTO.toDTO(savedUser);
    }
    public UserDTO createUserRoleUser() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("MariaAbreu")
                .cpf("27643914044")
                .email("teste@test.com")
                .password("12345678")
                .build();

        Role role = roleService.findByRoleName(RoleType.ROLE_USER);

        User user = UserRequestDTO.toModel(userRequestDTO).toBuilder()
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        User savedUser = userRepository.save(user);
        return UserDTO.toDTO(savedUser);
    }

    public LoginDTO createLogin() {
        LoginDTO login = LoginDTO.builder()
                .username("JoelMaciel")
                .password("12345678")
                .build();
        return login;
    }
}