package joelmaciel.service_control.domain.exception;

import joelmaciel.service_control.domain.enums.RoleType;

public class RoleNotExistsException extends BusinessException {

    public RoleNotExistsException(String message) {
        super(message);
    }

    public RoleNotExistsException(RoleType roleType) {
        this(String.format("There is no role with that name saved in the database"));
    }
}
