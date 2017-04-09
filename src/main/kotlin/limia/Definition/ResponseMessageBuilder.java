package limia.Definition;
import limia.Definition.GlobalConstants.HTTPMessages;

import static limia.Definition.GlobalConstants.HTTPMessages.*;
import static limia.Definition.GlobalConstants.CRUD;

/**
 * Created by macbook on 9/4/17.
 */
public class ResponseMessageBuilder {

    public static String CREATE(Class t) {
        return t.getName().concat(CREATE);
    }

    public static String DELETE(Class t) {
        return t.getName().concat(DELETE);
    }

    public static String READ(Class t) {
        return t.getName().concat(READ);
    }

    public static String UPDATE(Class t) {
        return t.getName().concat(UPDATE);
    }

    public static String ERROR(Class t, CRUD crud) {
        String verb = "undefined";
        switch (crud) {
            case CREATE:
                verb = "creating";
            case READ:
                verb = "reading";
            case UPDATE:
                verb = "updating";
            case DELETE:
                verb = "deleting";
        }
        return "Error " + verb + " the " + t.getName();
    }
}
