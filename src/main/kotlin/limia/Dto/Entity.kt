package limia.Dto

/**
 * Created by macbook on 9/4/17.
 */
open class Entity {
    open var identifier: String? = null

    constructor(identifier: String) {
        this.identifier = identifier
    }

    constructor()
}
