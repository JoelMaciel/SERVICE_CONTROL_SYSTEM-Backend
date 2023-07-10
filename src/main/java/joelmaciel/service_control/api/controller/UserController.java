package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.controller.openapi.UserControllerOpenApi;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.dto.request.UserRequestUpdateDTO;
import joelmaciel.service_control.domain.service.RegistrationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserControllerOpenApi {

    private final RegistrationUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserDTO getOneUser(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserDTO update(@PathVariable Long userId, @RequestBody @Valid UserRequestUpdateDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.removeUser(userId);
    }
}
