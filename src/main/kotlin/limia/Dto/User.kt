package limia.Dto

/**
 * Created by macbook on 8/4/17.
 */
class User {
    var name: String? = null
    var email: String? = null
    var identifier: String? = null

    constructor(name: String, email: String, identifier: String) {
        this.name = name
        this.email = email
        this.identifier = identifier
    }

    constructor() {}
}
