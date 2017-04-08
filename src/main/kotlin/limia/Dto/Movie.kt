package limia.Dto

/**
 * Created by macbook on 8/4/17.
 */
class Movie {
    var themoviedb_id : String? = null
    var identifier : String? = null

    constructor(themoviedb_id: String?, identifier: String?) {
        this.themoviedb_id = themoviedb_id
        this.identifier = identifier
    }

    constructor()


}