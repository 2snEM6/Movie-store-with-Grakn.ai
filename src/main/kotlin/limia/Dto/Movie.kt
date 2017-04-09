package limia.Dto

/**
 * Created by macbook on 8/4/17.
 */
class Movie : Entity {
    var themoviedb_id : String? = null

    constructor(identifier: String, themoviedb_id: String?) : super(identifier) {
        this.themoviedb_id = themoviedb_id
    }

    constructor(themoviedb_id: String?) : super() {
        this.themoviedb_id = themoviedb_id
    }

    constructor() : super()


}