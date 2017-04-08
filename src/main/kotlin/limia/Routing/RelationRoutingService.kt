package limia.Routing

import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Dto.Relation
import limia.Response.Response

import spark.Spark.post

/**
 * Created by macbook on 9/4/17.
 */
class RelationRoutingService : RoutingService<Relation>(), IRoutingService<Relation> {

    private var userController: UserController = UserController()
    private var movieController: MovieController = MovieController()
    private var relationController: RelationController = RelationController()

    override fun initializeRoutes() {
        post("/*/:id0/*/:id1") { request, response ->
            val relation = relationController.createRelation(request)
            gson.toJson(Response(201, "Relation created", relation))
        }
    }
}
