package limia.Exception

import java.util.ArrayList

/**
 * Created by workstation on 28/04/2017.
 */
class InvalidParametersException : Exception {

    var parameters: ArrayList<String>? = null

    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) :
            super(message, cause, enableSuppression, writableStackTrace) {}
}
