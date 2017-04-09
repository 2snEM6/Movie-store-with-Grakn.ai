package limia.Dto

/**
 * Created by macbook on 8/4/17.
 */
class User : Entity {
    var name: String? = null
    var email: String? = null

    constructor(name: String, email: String, identifier: String) : super(identifier){
        this.name = name
        this.email = email
    }

    constructor()
}
