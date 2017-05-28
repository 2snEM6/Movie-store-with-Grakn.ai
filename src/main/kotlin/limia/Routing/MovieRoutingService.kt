package limia.Routing

import limia.Controller.MovieController
import limia.Definition.ResponseMessageBuilder.Companion.CREATE
import limia.Definition.ResponseMessageBuilder.Companion.DELETE
import limia.Definition.ResponseMessageBuilder.Companion.NOT_FOUND
import limia.Definition.ResponseMessageBuilder.Companion.READ
import limia.Definition.ResponseMessageBuilder.Companion.READ_ALL
import limia.Definition.ResponseMessageBuilder.Companion.ALREADY_EXISTS
import limia.Definition.ResponseMessageBuilder.Companion.BAD_REQUEST
import limia.Dto.Movie
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Exception.InvalidParametersException
import limia.Response.ErrorResponse
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
            post("") { req, _ ->
                var movie: Movie?
                val errors = ArrayList<String>()
                try {
                    movie = movieController.createMovie(req)
                } catch(e: EntityAlreadyExistsException) {
                    errors.add(ALREADY_EXISTS(type))
                    return@post gson.toJson(ErrorResponse(409, errors))
                } catch (e: InvalidParametersException) {
                    errors.add(BAD_REQUEST())
                    return@post gson.toJson(ErrorResponse(400, errors))
                }
                return@post gson.toJson(SuccessResponse(201, CREATE(type), movie))

            }

            get("") { _, res ->
                val movies : ArrayList<Movie> = movieController.readAllMovies()
                res.status(200)
                gson.toJson(SuccessResponse(200, READ_ALL(type), movies))
            }

            get("/:id") { req, _ ->
                var movie: Movie? = null
                var notFound = false
                val errors = ArrayList<String>()
                try {
                    movie = movieController.findMovie(req)
                } catch(e: EntityNotFoundException) {
                    notFound = true
                }
                if (!notFound)
                    return@get gson.toJson(SuccessResponse(200, READ(type), movie))
                else {
                    errors.add(NOT_FOUND(type))
                    return@get gson.toJson(ErrorResponse(404, errors))
                }
            }

            put("/:id") { _, _ ->
                TODO("not implemented")
            }

            delete("/:id") { req, _ ->
                var notFound = false
                val errors = ArrayList<String>()
                try {
                    movieController.deleteMovie(req)
                } catch(e: EntityNotFoundException) {
                    notFound = true
                }
                if (!notFound)
                    return@delete gson.toJson(SuccessResponse(204, DELETE(type), null))
                else {
                    errors.add(NOT_FOUND(type))
                    return@delete gson.toJson(ErrorResponse(404, errors))
                }
            }
        }
    }
}
