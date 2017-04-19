package limia.Service

import limia.Dao.MovieDao
import limia.Dao.UserDao
import limia.Dto.Movie
import limia.Dto.User
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Service.IUserService
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
class MovieService() : IMovieService {

    private var dao: MovieDao? = null

    @Throws(EntityAlreadyExistsException::class)
    override fun create(themoviedb_id: String): Movie {
        val movie = Movie(UUID.randomUUID().toString(), themoviedb_id)
        try {
            dao!!.existsBy(Movie::class, "themoviedb_id", themoviedb_id)
        } catch (e: EntityNotFoundException) {
            return dao!!.create(movie)
        }
        return movie
    }

    @Throws(EntityNotFoundException::class)
    override fun read(id: String): Movie {
        return dao!!.read(Movie::class, id) as Movie
    }

    override fun update(movie: Movie): Movie {
        return dao!!.update(movie)
    }

    override fun delete(id: String) {
        dao!!.delete(Movie::class, id)
    }

    override fun readAll(): ArrayList<Movie> {
        return dao!!.readAll(Movie::class.java)
    }

    init {
        dao = MovieDao()
    }
}
