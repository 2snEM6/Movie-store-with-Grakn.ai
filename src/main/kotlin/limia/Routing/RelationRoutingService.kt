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
import spark.Spark.delete
import java.util.*

/**
 * Created by macbook on 9/4/17.
 */
class RelationRoutingService : RoutingService<Relation>(), IRoutingService<Relation> {

    private var userController: UserController = UserController()
    private var movieController: MovieController = MovieController()
    private var relationController: RelationController = RelationController()

    override fun initializeRoutes() {
        post("/*/:id0/*/:id1/:relation") post@ { request, response ->
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

        get("/*/:id0/*/:id1/:relation") post@ { request, response ->
            var exists: Boolean = relationController.existsRelation(request)!!
            if (!exists){
                var errorResponse = ErrorResponse()
                errorResponse.code = 404
                errorResponse.errors.add(NOT_FOUND(Relation::class.java, READ))
                return@post gson.toJson(errorResponse)
            }
            gson.toJson(SuccessResponse(200, EXISTS(type), null))
        }



        get("/relations") { request, response ->
            val relations = relationController.readAll()

            if (relations.isEmpty()) {
                var errors = ArrayList<String>()
                errors.add(NOT_FOUND_ALL(type, READ))
                return@get gson.toJson(ErrorResponse(404, errors))
            }
            return@get gson.toJson(SuccessResponse(200, READ_ALL(type), relations))
        }


        get("/relations/id/:id") { request, response ->
            val relation = relationController.readRelationByID(request.params(":id"))
            if (relation != null) {
                return@get gson.toJson(SuccessResponse(200, READ(type), relation))
            }
            var errors = ArrayList<String>()
            errors.add(NOT_FOUND(type))
            return@get gson.toJson(ErrorResponse(404, errors = errors))
        }

        delete("/relations/id/:id") { request, repsonse ->
            var notFound = false
            var errors = ArrayList<String>()
            try {
                relationController.deleteRelationByID(request)
            } catch(e: EntityNotFoundException) {
                notFound = true
            }
            //TODO handle relation exception
            if (!notFound)
                return@delete gson.toJson(SuccessResponse(204, DELETE(type), null))
            else {
                errors.add(NOT_FOUND(type))
                return@delete gson.toJson(ErrorResponse(404, errors))
            }
        }

        get("/relations/name/:name") { request, response ->
            val relations = relationController.findAllRelationsByName(request)
            var jsonBody : String?
            if (relations.isEmpty()) {
                var errors = ArrayList<String>()
                errors.add(NOT_FOUND_ALL(type, READ))
                jsonBody = gson.toJson(ErrorResponse(404, errors = errors))
            }
            else jsonBody = gson.toJson(SuccessResponse(200, READ_ALL(type), relations))
            jsonBody
        }
    }
}
