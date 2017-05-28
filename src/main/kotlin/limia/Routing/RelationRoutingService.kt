package limia.Routing

import limia.Controller.RelationController
import limia.Dto.Relation
import limia.Response.SuccessResponse
import limia.Definition.GlobalConstants.CRUD.*
import limia.Definition.ResponseMessageBuilder.Companion.NOT_FOUND
import limia.Definition.ResponseMessageBuilder.Companion.NOT_FOUND_ALL
import limia.Definition.ResponseMessageBuilder.Companion.EXISTS
import limia.Definition.ResponseMessageBuilder.Companion.READ_ALL
import limia.Definition.ResponseMessageBuilder.Companion.READ
import limia.Definition.ResponseMessageBuilder.Companion.DELETE
import limia.Definition.ResponseMessageBuilder.Companion.CREATE
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

    private var relationController: RelationController = RelationController()

    override fun initializeRoutes() {
        post("/*/:id0/*/:id1/:relation") post@ { request, _ ->
            val relation: Relation?
            try {
                relation = relationController.createRelation(request)
            } catch(e: EntityNotFoundException) {
                val errorResponse = ErrorResponse()
                errorResponse.code = 404
                e.types.forEach { k ->
                    errorResponse.errors.add(NOT_FOUND(k.java, READ)!!)
                }
                return@post gson.toJson(errorResponse)
            }
            gson.toJson(SuccessResponse(201, CREATE(type), relation))
        }

        get("/*/:id0/*/:id1/:relation") post@ { request, _ ->
            val exists: Boolean = relationController.existsRelation(request)!!
            if (!exists){
                val errorResponse = ErrorResponse()
                errorResponse.code = 404
                errorResponse.errors.add(NOT_FOUND(Relation::class.java, READ)!!)
                return@post gson.toJson(errorResponse)
            }
            gson.toJson(SuccessResponse(200, EXISTS(type), null))
        }



        get("/relations") { _, _ ->
            val relations = relationController.readAll()

            if (relations.isEmpty()) {
                val errors = ArrayList<String>()
                errors.add(NOT_FOUND_ALL(type, READ)!!)
                return@get gson.toJson(ErrorResponse(404, errors))
            }
            return@get gson.toJson(SuccessResponse(200, READ_ALL(type), relations))
        }


        get("/relations/id/:id") { request, _ ->
            var notFound = false
            var relation: Relation? = null
            try {
                relation = relationController.readRelationByID(request.params(":id"))
            } catch(e: EntityNotFoundException) {
                notFound = true
            }
            if (!notFound) {
                return@get gson.toJson(SuccessResponse(200, READ(type), relation))
            }
            val errors = ArrayList<String>()
            errors.add(NOT_FOUND(type))
            return@get gson.toJson(ErrorResponse(404, errors = errors))
        }

        delete("/relations/id/:id") { request, _ ->
            var notFound = false
            val errors = ArrayList<String>()
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

        get("/relations/name/:name") { request, _ ->
            val relations = relationController.findAllRelationsByName(request)
            val jsonBody : String?
            if (relations.isEmpty()) {
                val errors = ArrayList<String>()
                errors.add(NOT_FOUND_ALL(type, READ)!!)
                jsonBody = gson.toJson(ErrorResponse(404, errors = errors))
            }
            else jsonBody = gson.toJson(SuccessResponse(200, READ_ALL(type), relations))
            jsonBody
        }
    }
}
