package limia.Exception;

import kotlin.reflect.KClass;

import java.util.ArrayList;

/**
 * Created by workstation on 16/04/2017.
 */
public class EntityNotFoundException extends Exception {

    private ArrayList<KClass> types = new ArrayList<KClass>();

    public ArrayList<KClass>  getTypes() {
        return types;
    }

    public void setTypes(ArrayList<KClass>  types) {
        this.types = types;
    }

    public void addEntityType(KClass type) {
        types.add(type);
    }

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
