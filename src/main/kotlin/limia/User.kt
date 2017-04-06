package limia

/**
 * Created by workstation on 05/04/2017.
 */
data class User (val name: String?, val email: String?, val id: String?) {
    constructor() : this(null,null, null)
}