package limia.Exception;

/**
 * Created by workstation on 16/04/2017.
 */
public class EntityAlreadyExistsException extends Exception{

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
