package limia.Exception

import kotlin.reflect.KClass

import java.util.ArrayList

/**
 * Created by workstation on 16/04/2017.
 */
class EntityNotFoundException : Exception {

    var types = ArrayList<KClass<*>>()

    fun addEntityType(type: KClass<*>) {
        types.add(type)
    }

    constructor()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean)
            : super(message, cause, enableSuppression, writableStackTrace)
}
