package limia.Routing

import com.google.gson.Gson
import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Dto.Movie
import limia.Dto.User
import limia.Response.Response
import spark.Spark.*


/**
 * Created by macbook on 8/4/17.
 */
class MovieRoutingService : RoutingService<Movie>(), IRoutingService<Movie> {

    private var userController : UserController = UserController()
    private var movieController: MovieController = MovieController()
    private var relationController: RelationController = RelationController()

    override fun initializeRoutes() {

        path("/movies") {
            post("") { req, res ->
                val movie = movieController.createMovie(req)
                gson.toJson(Response(201, "Movie created", movie))
            }

            get("/:id") { req, res ->
                val movie = movieController.findMovie(req)
                gson.toJson(Response(200, null, movie))
            }

            put("/:id") { req, res ->
                TODO("not implemented")
            }

            delete("/:id") { req, res ->
                movieController.deleteMovie(req)
                gson.toJson(Response(401, "Movie deleted", null))
            }
        }
    }
}