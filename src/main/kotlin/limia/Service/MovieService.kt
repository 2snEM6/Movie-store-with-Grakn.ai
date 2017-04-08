package limia.Service

import limia.Dao.MovieDao
import limia.Dao.UserDao
import limia.Dto.Movie
import limia.Dto.User
import limia.Service.IUserService

import java.util.UUID

/**
 * Created by workstation on 05/04/2017.
 */
class MovieService() : IMovieService {

    private var dao: MovieDao? = null

    override fun create(themoviedb_id: String): Movie {
        val movie = Movie(themoviedb_id,UUID.randomUUID().toString())
        return dao!!.create(movie)
    }

    override fun read(id: String): Movie {
        return dao!!.read(id)
    }

    override fun update(movie: Movie): Movie {
        return dao!!.update(movie)
    }

    override fun delete(id: String) {
        dao!!.delete(id)
    }

    init {
        dao = MovieDao()
    }
}
