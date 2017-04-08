package limia.Dto

/**
 * Created by macbook on 8/4/17.
 */
class Relation {
    var firstEntityID : String? = null
    var secondEntityID : String? = null
    var relationName : String? = null
    var firstRole : String? = null
    var secondRole : String? = null

    constructor(firstEntityID: String?, secondEntityID: String?, relationName: String?,
                firstRole: String?, secondRole: String?) {
        this.firstEntityID = firstEntityID
        this.secondEntityID = secondEntityID
        this.relationName = relationName
        this.firstRole = firstRole
        this.secondRole = secondRole
    }

    constructor()


}