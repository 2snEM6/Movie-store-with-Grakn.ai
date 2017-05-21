package limia.Exception

import java.util.ArrayList

/**
 * Created by workstation on 16/04/2017.
 */
class EntityAlreadyExistsException : Exception {


    var types: ArrayList<Class<*>>? = null

    fun addEntityType(type: Class<*>) {
        types!!.add(type)
    }

    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
