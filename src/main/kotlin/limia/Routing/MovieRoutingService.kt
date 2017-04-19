package limia.Routing

import com.google.gson.Gson
import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.ResponseMessageBuilder.*
import limia.Dto.Movie
import limia.Dto.User
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Response.SuccessResponse
import spark.Spark.*
import java.util.*


/**
 * Created by macbook on 8/4/17.
 */
class MovieRoutingService : RoutingService<Movie>(), IRoutingService<Movie> {

    private var movieController: MovieController = MovieController()

    override fun initializeRoutes() {

        path("/movies") {
            post("") { req, res ->
                var movie: Movie? = null
                var alreadyExists = false;
                try {
                    movie = movieController.createMovie(req)
                } catch(e: EntityAlreadyExistsException) {
                    alreadyExists = true;
                }
                if (!alreadyExists)
                    gson.toJson(SuccessResponse(201, CREATE(type), movie))
                else
                    gson.toJson(SuccessResponse(409, ALREADY_EXISTS(type), null))

            }

            get("") { req, res ->
                val movies : ArrayList<Movie> = movieController?.readAllMovies()
                res.status(200)
                gson.toJson(SuccessResponse(200, READ_ALL(type), movies))
            }

            get("/:id") { req, res ->
                var movie: Movie? = null
                var notFound = false
                try {
                    movie = movieController.findMovie(req)
                } catch(e: EntityNotFoundException) {
                    notFound = true
                }
                if (!notFound)
                    gson.toJson(SuccessResponse(200, READ(type), movie))
                else
                    gson.toJson(SuccessResponse(404, NOT_FOUND(type), null))
            }

            put("/:id") { req, res ->
                TODO("not implemented")
            }

            delete("/:id") { req, res ->
                var notFound = false
                try {
                    movieController.deleteMovie(req)
                } catch(e: EntityNotFoundException) {
                    notFound = true
                }
                if (!notFound)
                    gson.toJson(SuccessResponse(204, DELETE(type), null))
                else
                    gson.toJson(SuccessResponse(404, NOT_FOUND(type), null))
            }
        }
    }
}
