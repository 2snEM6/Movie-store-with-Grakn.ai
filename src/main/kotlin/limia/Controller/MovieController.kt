package limia.Controller

import limia.Dto.Movie
import limia.Dto.User
import limia.Service.MovieService
import limia.Service.UserService
import spark.Request
import java.util.*

/**
 * Created by macbook on 8/4/17.
 */
class MovieController {

    private val movieService: MovieService = MovieService()

    fun createMovie(request: Request): Movie {
        val themoviedb_id = request.queryParams("themoviedb_id")
        return movieService.create(themoviedb_id)
    }

    fun findMovie(request: Request): Movie {
        return movieService.read(request.params(":id"))
    }

    fun deleteMovie(request: Request) {
        movieService.delete(request.params(":id"))
    }

    fun updateMovie(request: Request) {
        val movie = Movie()
        if (request.queryParams().contains("themoviedb_id")) movie.themoviedb_id = request.queryParams("themoviedb_id")
        movie.identifier = request.params(":id")
        movieService.update(movie)
    }

    fun readAllMovies(): ArrayList<Movie> {
        return movieService.readAll()
    }

}
