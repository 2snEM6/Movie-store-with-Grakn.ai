package limia.Routing;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by macbook on 8/4/17.
 */
public abstract class RoutingService<T> implements IRoutingService<T> {

    protected Class<T> type;
    protected Gson gson;

    public RoutingService() {
        Type t = getClass().getGenericSuperclass();
        gson = new Gson();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
}
