package limia.Routing

import com.google.gson.Gson
import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.ResponseMessageBuilder.*
import limia.Dto.Movie
import limia.Dto.User
import limia.Response.Response
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
                val movie = movieController.createMovie(req)
                gson.toJson(Response(201, CREATE(type), movie))
            }

            get("") { req, res ->
                val movies : ArrayList<Movie> = movieController?.readAllMovies()
                res.status(200)
                gson.toJson(Response(200, READ_ALL(type), movies))
            }

            get("/:id") { req, res ->
                val movie = movieController.findMovie(req)
                gson.toJson(Response(200, READ(type), movie))
            }

            put("/:id") { req, res ->
                TODO("not implemented")
            }

            delete("/:id") { req, res ->
                movieController.deleteMovie(req)
                gson.toJson(Response(401, UPDATE(type), null))
            }
        }
    }
}
