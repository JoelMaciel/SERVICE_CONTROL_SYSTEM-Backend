package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.dto.request.UserRequestDTO;
import joelmaciel.service_control.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserRequestDTO userRequest) {
         userService.save(userRequest);
    }
}
