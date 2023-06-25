package joelmaciel.service_control.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistsException extends BusinessException{
    public EntityNotExistsException(String message) {
        super(message);
    }
}
