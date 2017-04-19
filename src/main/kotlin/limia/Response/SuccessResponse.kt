package limia.Response

/**
 * Created by macbook on 8/4/17.
 */
class SuccessResponse : Response {

    var message: String? = null
    var data: Any? = null

    constructor() : super() {}
    constructor(code: Int?, message: String?, data: Any?) : super(code) {
        this.message = message
        this.data = data
    }
}
