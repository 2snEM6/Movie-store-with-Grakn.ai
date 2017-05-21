package limia.Grakn

import ai.grakn.concept.Entity
import ai.grakn.concept.Resource
import com.google.gson.Gson
import limia.Dto.User

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.HashMap
import org.apache.tinkerpop.gremlin.structure.T



/**
 * Created by macbook on 8/4/17.
 */
class EntityMapper<T : Any>(protected var type: Class<T>) : IEntityMapper<T> {


    fun getField(clazz: Class<*>?, name: String): Field? {
        var clazz = clazz
        var field: Field? = null
        while (clazz != null && field == null) {
            try {
                field = clazz.getDeclaredField(name)
            } catch (e: Exception) {
            }

            clazz = clazz.superclass
        }
        return field
    }


    override fun extractNonNullFields(t: T): Map<String, Any> {
        val fields = HashMap<String, Any>()
        for (field in t.javaClass.declaredFields) {
            try {
                field.isAccessible = true
                if (field.get(t) != null) {
                    fields.put(field.name, field.get(t))
                }
                field.isAccessible = false
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        return fields
    }

    override fun fromEntity(entity: Entity): T? {
        var t: T? = null
        try {
            t = type.newInstance()
        } catch (e: InstantiationException) {
            println("Unable to instantiate")
        } catch (e: IllegalAccessException) {
            println("Unable to instantiate")
        }


        for (resource in entity.resources()) {
            val resourceName = resource.type().name.toString()
            var field = getField(type, resourceName)
            if (field != null) {
                val fieldName = field.name
                if (resourceName == fieldName) {
                    try {
                        field.isAccessible = true
                        field.set(t, resource.value)
                        field.isAccessible = false
                    } catch (e: IllegalAccessException) {
                        println("Unable to parse field")
                    }
                }
            }
        }
        return t
    }

    override fun toEntity(t: T): Entity? {
        return null
    }
}
