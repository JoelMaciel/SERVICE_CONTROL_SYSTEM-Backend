package joelmaciel.service_control.domain.exception;


public class UserNotFoundException extends EntityNotExistsException{

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        this(String.format("There is no register of user with code %d", userId));
    }
}
