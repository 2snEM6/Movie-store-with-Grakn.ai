package limia.Routing

import com.google.gson.Gson

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by macbook on 8/4/17.
 */
abstract class RoutingService<T> : IRoutingService<T> {

    protected var type: Class<T>
    protected var gson: Gson

    init {
        val t = javaClass.genericSuperclass
        gson = Gson()
        val pt = t as ParameterizedType
        type = pt.actualTypeArguments[0] as Class<T>
    }
}
