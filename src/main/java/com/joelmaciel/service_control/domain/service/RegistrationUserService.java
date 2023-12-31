package com.joelmaciel.service_control.domain.service;

import com.joelmaciel.service_control.api.dto.UserDTO;
import com.joelmaciel.service_control.api.dto.request.UserRequestDTO;
import com.joelmaciel.service_control.api.dto.request.UserRequestUpdateDTO;
import com.joelmaciel.service_control.api.security.JwtProvider;
import com.joelmaciel.service_control.api.security.dto.JwtDTO;
import com.joelmaciel.service_control.api.security.dto.LoginDTO;
import com.joelmaciel.service_control.domain.enums.RoleType;
import com.joelmaciel.service_control.domain.exception.InvalidLoginDataException;
import com.joelmaciel.service_control.domain.exception.UserNotFoundException;
import com.joelmaciel.service_control.domain.model.Role;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationUserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final List<Validator<UserRequestDTO>> listUserValidator;

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::toDTO)
                .toList();
    }

    public UserDTO findById(Long userId) {
        User user = searchById(userId);
        return UserDTO.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long userId, @RequestBody UserRequestUpdateDTO userRequestUpdateDTO) {
        User currentUser = searchById(userId).toBuilder()
                .username(userRequestUpdateDTO.getUsername())
                .email(userRequestUpdateDTO.getEmail())
                .build();

        return UserDTO.toDTO(userRepository.save(currentUser));
    }

    @Transactional
    public UserDTO saveUser(UserRequestDTO userRequestDTO) {
        listUserValidator.forEach(validator -> validator.validate(userRequestDTO));

        Role role = roleService.findByRoleName(RoleType.ROLE_USER);

        User user = UserRequestDTO.toModel(userRequestDTO).toBuilder()
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        return UserDTO.toDTO(userRepository.save(user));
    }

    @Transactional
    public void removeUser(Long userId) {
        searchById(userId);
        userRepository.deleteById(userId);
    }

    public JwtDTO authenticationUserLogin(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new JwtDTO(jwtProvider.generateJwt(authentication));
        } catch (RuntimeException e) {
            throw new InvalidLoginDataException("Incorrect username or password.");
        }

    }

    public User searchById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
