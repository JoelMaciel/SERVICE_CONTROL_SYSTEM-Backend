package joelmaciel.service_control.domain.exception;


public class ClientNotFoundException extends EntityNotExistsException{

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(Long clientId) {
        this(String.format("There is no register of client with code %d", clientId));
    }
}
