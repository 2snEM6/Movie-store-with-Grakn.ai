package limia.Routing

import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.GlobalConstants
import limia.Dto.Relation
import limia.Response.SuccessResponse
import limia.Definition.GlobalConstants.CRUD.*;
import limia.Definition.ResponseMessageBuilder.*
import limia.Exception.EntityNotFoundException
import limia.Response.ErrorResponse
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
        post("/*/:id0/*/:id1") post@ { request, response ->
            var relation: Relation? = null
            try {
                relation = relationController.createRelation(request)
            } catch(e: EntityNotFoundException) {
                var errorResponse = ErrorResponse()
                errorResponse.code = 404
                e.types.forEach { k ->
                    errorResponse.errors.add(NOT_FOUND(k.java, READ))
                }
                return@post gson.toJson(errorResponse)
            }
            gson.toJson(SuccessResponse(201, CREATE(type), relation))
        }

        get("/relations/:name") { request, response ->
            val relations = relationController.findAllRelationsByName(request)
            var jsonBody : String?
            if (relations.isEmpty())
                jsonBody = gson.toJson(SuccessResponse(404, NOT_FOUND_ALL(type, READ),null))
            else jsonBody = gson.toJson(SuccessResponse(200, READ_ALL(type), relations))
            jsonBody
        }
    }
}
