package limia.Exception;

import java.util.ArrayList;

/**
 * Created by workstation on 16/04/2017.
 */
public class EntityAlreadyExistsException extends Exception{


    private ArrayList<Class> types;

    public ArrayList<Class> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Class> types) {
        this.types = types;
    }

    public void addEntityType(Class type) {
        types.add(type);
    }

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
