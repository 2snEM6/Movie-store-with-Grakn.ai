package limia.Service

import limia.Dto.Movie
import limia.Dto.User
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
interface IMovieService {
    fun create(themoviedb_id: String): Movie

    fun read(id: String): Movie

    fun update(movie: Movie): Movie

    fun delete(id: String)

    fun readAll(): ArrayList<Movie>
}
