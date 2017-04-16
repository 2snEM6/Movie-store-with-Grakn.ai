package limia.Routing

import com.google.gson.Gson
import limia.Controller.MovieController
import limia.Controller.RelationController
import limia.Controller.UserController
import limia.Definition.ResponseMessageBuilder.*;
import limia.Dto.User
import limia.Exception.EntityAlreadyExistsException
import limia.Response.Response
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
                var alreadyExists = false;
                try {
                    user = userController?.createUser(req)
                } catch(e: EntityAlreadyExistsException) {
                   alreadyExists = true;
                }
                if (!alreadyExists)
                    gson.toJson(Response(201, CREATE(type), user))
                else
                    gson.toJson(Response(409, ALREADY_EXISTS(type), null))
            }

            get("") { req, res ->
                val users : ArrayList<User> = userController?.readAllUsers()
                gson.toJson(Response(200, READ_ALL(type), users))
            }

            get("/:id") { req, res ->
                var user = userController?.findUser(req)
                gson.toJson(Response(200, READ(type), user))
            }

            put("/:id") { req, res ->
                userController?.updateUser(req)
                gson.toJson(Response(200, UPDATE(type), null))
            }

            delete("/:id") { req, res ->
                userController?.deleteUser(req)
                gson.toJson(Response(204, DELETE(type), null))
            }
        }
    }
}
