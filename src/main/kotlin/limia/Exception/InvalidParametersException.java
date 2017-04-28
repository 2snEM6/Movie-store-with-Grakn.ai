package limia.Exception;

import java.util.ArrayList;

/**
 * Created by workstation on 28/04/2017.
 */
public class InvalidParametersException extends Exception {

    private ArrayList<String> parameters;

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public InvalidParametersException() {
    }

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParametersException(Throwable cause) {
        super(cause);
    }

    public InvalidParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
