package joelmaciel.service_control.domain.exception;

public class CpfAlreadyExistsException extends EntityInUseException {

    public CpfAlreadyExistsException(String cpf) {
        super(String.format("There is already a client registered with this CPF :  %s  ", cpf));
    }

}
