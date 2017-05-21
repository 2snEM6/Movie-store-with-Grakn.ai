package limia.Routing

import com.google.gson.Gson
import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.ResponseMessageBuilder.*;
import limia.Dto.User
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
class UserRoutingService : RoutingService<User>(), IRoutingService<User> {

    private var userController: UserController = UserController()

    override fun initializeRoutes() {

        path("/users") {
            post("") { req, res ->
                var user: User? = null
                var errors = ArrayList<String>()
                var alreadyExists = false;
                var invalidParams = false;
                try {
                    user = userController?.createUser(req)
                } catch(e: EntityAlreadyExistsException) {
                    alreadyExists = true;
                } catch (e: InvalidParametersException) {
                    invalidParams = true;
                }
                val errorsPresent = alreadyExists or invalidParams

                if (errorsPresent) {
                    if (alreadyExists) {
                        errors.add(ALREADY_EXISTS(type))
                        return@post gson.toJson(ErrorResponse(409, errors))
                    }
                    if (invalidParams) {
                        errors.add(BAD_REQUEST())
                        return@post gson.toJson(ErrorResponse(400, errors))
                    }
                }
                return@post gson.toJson(SuccessResponse(201, CREATE(type), user))
            }

            get("") { req, res ->
                val users : ArrayList<User> = userController?.readAllUsers()
                gson.toJson(SuccessResponse(200, READ_ALL(type), users))
            }


            get("/:id") { req, res ->
                var user: User? = null
                var notFound = false
                var errors = ArrayList<String>()
                try {
                    user = userController?.findUser(req)
                } catch(e: EntityNotFoundException) {
                    notFound = true
                }
                if (!notFound)
                    return@get gson.toJson(SuccessResponse(200, READ(type), user))
                else {
                    errors.add(NOT_FOUND(type))
                    return@get gson.toJson(ErrorResponse(404, errors))
                }
            }

            put("/:id") { req, res ->
                userController?.updateUser(req)
                gson.toJson(SuccessResponse(200, UPDATE(type), null))
            }

            delete("/:id") { req, res ->
                var notFound = false
                var errors = ArrayList<String>()
                try {
                    userController?.deleteUser(req)
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
