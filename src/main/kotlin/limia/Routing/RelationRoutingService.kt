package limia.Routing

import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.GlobalConstants
import limia.Dto.Relation
import limia.Response.Response
import limia.Definition.GlobalConstants.CRUD.*;
import limia.Definition.ResponseMessageBuilder.*
import spark.Spark.get

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
            var jsonBody : String?
            if (relation != null)
                jsonBody = gson.toJson(Response(201, CREATE(type), relation))
            else jsonBody = gson.toJson(Response(404, ERROR(type,CREATE), null))
            jsonBody
        }

        get("/relations/:name") { request, response ->
            val relations = relationController.findAllRelationsByName(request)
            var jsonBody : String?
            if (relations.isEmpty())
                jsonBody = gson.toJson(Response(404, NOT_FOUND_ALL(type, READ),null))
            else jsonBody = gson.toJson(Response(200, READ_ALL(type), relations))
            jsonBody
        }
    }
}
