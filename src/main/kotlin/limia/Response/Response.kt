package limia.Response

/**
 * Created by macbook on 8/4/17.
 */
class Response {
    var code: Int = 0
    var message: String? = null
    var data: Any? = null

    constructor(code: Int, message: String?, data: Any?) {
        this.code = code
        this.message = message
        this.data = data
    }

    constructor() {}
}
