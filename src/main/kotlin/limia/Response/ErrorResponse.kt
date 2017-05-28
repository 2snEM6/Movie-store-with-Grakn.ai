package limia.Response

import java.util.*

/**
 * Created by workstation on 20/04/2017.
 */
class ErrorResponse : Response {
    var errors : ArrayList<String> = ArrayList()

    constructor(code: Int?, errors: ArrayList<String>) : super(code) {
        this.errors = errors
    }

    constructor()
}
